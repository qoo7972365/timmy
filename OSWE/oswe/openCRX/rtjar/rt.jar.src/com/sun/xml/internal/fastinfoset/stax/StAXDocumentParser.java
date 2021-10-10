/*      */ package com.sun.xml.internal.fastinfoset.stax;
/*      */ 
/*      */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*      */ import com.sun.xml.internal.fastinfoset.Decoder;
/*      */ import com.sun.xml.internal.fastinfoset.DecoderStateTables;
/*      */ import com.sun.xml.internal.fastinfoset.OctetBufferListener;
/*      */ import com.sun.xml.internal.fastinfoset.QualifiedName;
/*      */ import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
/*      */ import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithmFactory;
/*      */ import com.sun.xml.internal.fastinfoset.org.apache.xerces.util.XMLChar;
/*      */ import com.sun.xml.internal.fastinfoset.sax.AttributesHolder;
/*      */ import com.sun.xml.internal.fastinfoset.util.CharArray;
/*      */ import com.sun.xml.internal.fastinfoset.util.CharArrayString;
/*      */ import com.sun.xml.internal.fastinfoset.util.PrefixArray;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithm;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithmException;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetException;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.stax.FastInfosetStreamReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
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
/*      */ public class StAXDocumentParser
/*      */   extends Decoder
/*      */   implements XMLStreamReader, FastInfosetStreamReader, OctetBufferListener
/*      */ {
/*   71 */   private static final Logger logger = Logger.getLogger(StAXDocumentParser.class.getName());
/*      */ 
/*      */   
/*      */   protected static final int INTERNAL_STATE_START_DOCUMENT = 0;
/*      */   
/*      */   protected static final int INTERNAL_STATE_START_ELEMENT_TERMINATE = 1;
/*      */   
/*      */   protected static final int INTERNAL_STATE_SINGLE_TERMINATE_ELEMENT_WITH_NAMESPACES = 2;
/*      */   
/*      */   protected static final int INTERNAL_STATE_DOUBLE_TERMINATE_ELEMENT = 3;
/*      */   
/*      */   protected static final int INTERNAL_STATE_END_DOCUMENT = 4;
/*      */   
/*      */   protected static final int INTERNAL_STATE_VOID = -1;
/*      */   
/*      */   protected int _internalState;
/*      */   
/*      */   protected int _eventType;
/*      */   
/*   90 */   protected QualifiedName[] _qNameStack = new QualifiedName[32];
/*   91 */   protected int[] _namespaceAIIsStartStack = new int[32];
/*   92 */   protected int[] _namespaceAIIsEndStack = new int[32];
/*   93 */   protected int _stackCount = -1;
/*      */   
/*   95 */   protected String[] _namespaceAIIsPrefix = new String[32];
/*   96 */   protected String[] _namespaceAIIsNamespaceName = new String[32];
/*   97 */   protected int[] _namespaceAIIsPrefixIndex = new int[32];
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _namespaceAIIsIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _currentNamespaceAIIsStart;
/*      */ 
/*      */   
/*      */   protected int _currentNamespaceAIIsEnd;
/*      */ 
/*      */   
/*      */   protected QualifiedName _qualifiedName;
/*      */ 
/*      */   
/*  114 */   protected AttributesHolder _attributes = new AttributesHolder();
/*      */   
/*      */   protected boolean _clearAttributes = false;
/*      */   
/*      */   protected char[] _characters;
/*      */   
/*      */   protected int _charactersOffset;
/*      */   
/*      */   protected String _algorithmURI;
/*      */   
/*      */   protected int _algorithmId;
/*      */   
/*      */   protected boolean _isAlgorithmDataCloned;
/*      */   
/*      */   protected byte[] _algorithmData;
/*      */   
/*      */   protected int _algorithmDataOffset;
/*      */   
/*      */   protected int _algorithmDataLength;
/*      */   
/*      */   protected String _piTarget;
/*      */   
/*      */   protected String _piData;
/*  137 */   protected NamespaceContextImpl _nsContext = new NamespaceContextImpl();
/*      */   
/*      */   protected String _characterEncodingScheme;
/*      */   
/*      */   protected StAXManager _manager;
/*      */   
/*      */   private byte[] base64TaleBytes;
/*      */   
/*      */   private int base64TaleLength;
/*      */ 
/*      */   
/*      */   public StAXDocumentParser(InputStream s) {
/*  149 */     this();
/*  150 */     setInputStream(s);
/*  151 */     this._manager = new StAXManager(1);
/*      */   }
/*      */   
/*      */   public StAXDocumentParser(InputStream s, StAXManager manager) {
/*  155 */     this(s);
/*  156 */     this._manager = manager;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInputStream(InputStream s) {
/*  161 */     super.setInputStream(s);
/*  162 */     reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public void reset() {
/*  167 */     super.reset();
/*  168 */     if (this._internalState != 0 && this._internalState != 4) {
/*      */ 
/*      */       
/*  171 */       for (int i = this._namespaceAIIsIndex - 1; i >= 0; i--) {
/*  172 */         this._prefixTable.popScopeWithPrefixEntry(this._namespaceAIIsPrefixIndex[i]);
/*      */       }
/*      */       
/*  175 */       this._stackCount = -1;
/*      */       
/*  177 */       this._namespaceAIIsIndex = 0;
/*  178 */       this._characters = null;
/*  179 */       this._algorithmData = null;
/*      */     } 
/*      */     
/*  182 */     this._characterEncodingScheme = "UTF-8";
/*  183 */     this._eventType = 7;
/*  184 */     this._internalState = 0;
/*      */   }
/*      */   
/*      */   protected void resetOnError() {
/*  188 */     super.reset();
/*      */     
/*  190 */     if (this._v != null) {
/*  191 */       this._prefixTable.clearCompletely();
/*      */     }
/*  193 */     this._duplicateAttributeVerifier.clear();
/*      */     
/*  195 */     this._stackCount = -1;
/*      */     
/*  197 */     this._namespaceAIIsIndex = 0;
/*  198 */     this._characters = null;
/*  199 */     this._algorithmData = null;
/*      */     
/*  201 */     this._eventType = 7;
/*  202 */     this._internalState = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getProperty(String name) throws IllegalArgumentException {
/*  209 */     if (this._manager != null) {
/*  210 */       return this._manager.getProperty(name);
/*      */     }
/*  212 */     return null; } public int next() throws XMLStreamException {
/*      */     try {
/*      */       QualifiedName qn;
/*      */       boolean addToTable;
/*      */       int index, b2;
/*  217 */       if (this._internalState != -1) {
/*  218 */         int i; switch (this._internalState) {
/*      */           case 0:
/*  220 */             decodeHeader();
/*  221 */             processDII();
/*      */             
/*  223 */             this._internalState = -1;
/*      */             break;
/*      */           case 1:
/*  226 */             if (this._currentNamespaceAIIsEnd > 0) {
/*  227 */               for (int j = this._currentNamespaceAIIsEnd - 1; j >= this._currentNamespaceAIIsStart; j--) {
/*  228 */                 this._prefixTable.popScopeWithPrefixEntry(this._namespaceAIIsPrefixIndex[j]);
/*      */               }
/*  230 */               this._namespaceAIIsIndex = this._currentNamespaceAIIsStart;
/*      */             } 
/*      */ 
/*      */             
/*  234 */             popStack();
/*      */             
/*  236 */             this._internalState = -1;
/*  237 */             return this._eventType = 2;
/*      */           
/*      */           case 2:
/*  240 */             for (i = this._currentNamespaceAIIsEnd - 1; i >= this._currentNamespaceAIIsStart; i--) {
/*  241 */               this._prefixTable.popScopeWithPrefixEntry(this._namespaceAIIsPrefixIndex[i]);
/*      */             }
/*  243 */             this._namespaceAIIsIndex = this._currentNamespaceAIIsStart;
/*  244 */             this._internalState = -1;
/*      */             break;
/*      */           
/*      */           case 3:
/*  248 */             if (this._currentNamespaceAIIsEnd > 0) {
/*  249 */               for (i = this._currentNamespaceAIIsEnd - 1; i >= this._currentNamespaceAIIsStart; i--) {
/*  250 */                 this._prefixTable.popScopeWithPrefixEntry(this._namespaceAIIsPrefixIndex[i]);
/*      */               }
/*  252 */               this._namespaceAIIsIndex = this._currentNamespaceAIIsStart;
/*      */             } 
/*      */             
/*  255 */             if (this._stackCount == -1) {
/*  256 */               this._internalState = 4;
/*  257 */               return this._eventType = 8;
/*      */             } 
/*      */ 
/*      */             
/*  261 */             popStack();
/*      */             
/*  263 */             this._internalState = (this._currentNamespaceAIIsEnd > 0) ? 2 : -1;
/*      */ 
/*      */             
/*  266 */             return this._eventType = 2;
/*      */           case 4:
/*  268 */             throw new NoSuchElementException(CommonResourceBundle.getInstance().getString("message.noMoreEvents"));
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/*  273 */       this._characters = null;
/*  274 */       this._algorithmData = null;
/*  275 */       this._currentNamespaceAIIsEnd = 0;
/*      */ 
/*      */       
/*  278 */       int b = read();
/*  279 */       switch (DecoderStateTables.EII(b)) {
/*      */         case 0:
/*  281 */           processEII(this._elementNameTable._array[b], false);
/*  282 */           return this._eventType;
/*      */         case 1:
/*  284 */           processEII(this._elementNameTable._array[b & 0x1F], true);
/*  285 */           return this._eventType;
/*      */         case 2:
/*  287 */           processEII(processEIIIndexMedium(b), ((b & 0x40) > 0));
/*  288 */           return this._eventType;
/*      */         case 3:
/*  290 */           processEII(processEIIIndexLarge(b), ((b & 0x40) > 0));
/*  291 */           return this._eventType;
/*      */         
/*      */         case 5:
/*  294 */           qn = processLiteralQualifiedName(b & 0x3, this._elementNameTable
/*      */               
/*  296 */               .getNext());
/*  297 */           this._elementNameTable.add(qn);
/*  298 */           processEII(qn, ((b & 0x40) > 0));
/*  299 */           return this._eventType;
/*      */         
/*      */         case 4:
/*  302 */           processEIIWithNamespaces(((b & 0x40) > 0));
/*  303 */           return this._eventType;
/*      */         case 6:
/*  305 */           this._octetBufferLength = (b & 0x1) + 1;
/*      */           
/*  307 */           processUtf8CharacterString(b);
/*  308 */           return this._eventType = 4;
/*      */         case 7:
/*  310 */           this._octetBufferLength = read() + 3;
/*  311 */           processUtf8CharacterString(b);
/*  312 */           return this._eventType = 4;
/*      */         case 8:
/*  314 */           this
/*      */ 
/*      */             
/*  317 */             ._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */           
/*  319 */           processUtf8CharacterString(b);
/*  320 */           return this._eventType = 4;
/*      */         case 9:
/*  322 */           this._octetBufferLength = (b & 0x1) + 1;
/*      */           
/*  324 */           processUtf16CharacterString(b);
/*  325 */           return this._eventType = 4;
/*      */         case 10:
/*  327 */           this._octetBufferLength = read() + 3;
/*  328 */           processUtf16CharacterString(b);
/*  329 */           return this._eventType = 4;
/*      */         case 11:
/*  331 */           this
/*      */ 
/*      */             
/*  334 */             ._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */           
/*  336 */           processUtf16CharacterString(b);
/*  337 */           return this._eventType = 4;
/*      */         
/*      */         case 12:
/*  340 */           addToTable = ((b & 0x10) > 0);
/*      */           
/*  342 */           this._identifier = (b & 0x2) << 6;
/*  343 */           b2 = read();
/*  344 */           this._identifier |= (b2 & 0xFC) >> 2;
/*      */           
/*  346 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(b2);
/*      */           
/*  348 */           decodeRestrictedAlphabetAsCharBuffer();
/*      */           
/*  350 */           if (addToTable) {
/*  351 */             this._charactersOffset = this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*  352 */             this._characters = this._characterContentChunkTable._array;
/*      */           } else {
/*  354 */             this._characters = this._charBuffer;
/*  355 */             this._charactersOffset = 0;
/*      */           } 
/*  357 */           return this._eventType = 4;
/*      */ 
/*      */         
/*      */         case 13:
/*  361 */           addToTable = ((b & 0x10) > 0);
/*      */           
/*  363 */           this._algorithmId = (b & 0x2) << 6;
/*  364 */           b2 = read();
/*  365 */           this._algorithmId |= (b2 & 0xFC) >> 2;
/*      */           
/*  367 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(b2);
/*  368 */           processCIIEncodingAlgorithm(addToTable);
/*      */           
/*  370 */           if (this._algorithmId == 9) {
/*  371 */             return this._eventType = 12;
/*      */           }
/*      */           
/*  374 */           return this._eventType = 4;
/*      */ 
/*      */         
/*      */         case 14:
/*  378 */           index = b & 0xF;
/*  379 */           this._characterContentChunkTable._cachedIndex = index;
/*      */           
/*  381 */           this._characters = this._characterContentChunkTable._array;
/*  382 */           this._charactersOffset = this._characterContentChunkTable._offset[index];
/*  383 */           this._charBufferLength = this._characterContentChunkTable._length[index];
/*  384 */           return this._eventType = 4;
/*      */ 
/*      */         
/*      */         case 15:
/*  388 */           index = ((b & 0x3) << 8 | read()) + 16;
/*      */           
/*  390 */           this._characterContentChunkTable._cachedIndex = index;
/*      */           
/*  392 */           this._characters = this._characterContentChunkTable._array;
/*  393 */           this._charactersOffset = this._characterContentChunkTable._offset[index];
/*  394 */           this._charBufferLength = this._characterContentChunkTable._length[index];
/*  395 */           return this._eventType = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 16:
/*  401 */           index = ((b & 0x3) << 16 | read() << 8 | read()) + 1040;
/*      */           
/*  403 */           this._characterContentChunkTable._cachedIndex = index;
/*      */           
/*  405 */           this._characters = this._characterContentChunkTable._array;
/*  406 */           this._charactersOffset = this._characterContentChunkTable._offset[index];
/*  407 */           this._charBufferLength = this._characterContentChunkTable._length[index];
/*  408 */           return this._eventType = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 17:
/*  414 */           index = (read() << 16 | read() << 8 | read()) + 263184;
/*      */           
/*  416 */           this._characterContentChunkTable._cachedIndex = index;
/*      */           
/*  418 */           this._characters = this._characterContentChunkTable._array;
/*  419 */           this._charactersOffset = this._characterContentChunkTable._offset[index];
/*  420 */           this._charBufferLength = this._characterContentChunkTable._length[index];
/*  421 */           return this._eventType = 4;
/*      */         
/*      */         case 18:
/*  424 */           processCommentII();
/*  425 */           return this._eventType;
/*      */         case 19:
/*  427 */           processProcessingII();
/*  428 */           return this._eventType;
/*      */         
/*      */         case 21:
/*  431 */           processUnexpandedEntityReference(b);
/*      */           
/*  433 */           return next();
/*      */         
/*      */         case 23:
/*  436 */           if (this._stackCount != -1) {
/*      */             
/*  438 */             popStack();
/*      */             
/*  440 */             this._internalState = 3;
/*  441 */             return this._eventType = 2;
/*      */           } 
/*      */           
/*  444 */           this._internalState = 4;
/*  445 */           return this._eventType = 8;
/*      */         case 22:
/*  447 */           if (this._stackCount != -1) {
/*      */             
/*  449 */             popStack();
/*      */             
/*  451 */             if (this._currentNamespaceAIIsEnd > 0) {
/*  452 */               this._internalState = 2;
/*      */             }
/*  454 */             return this._eventType = 2;
/*      */           } 
/*      */           
/*  457 */           this._internalState = 4;
/*  458 */           return this._eventType = 8;
/*      */       } 
/*  460 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     }
/*  462 */     catch (IOException e) {
/*  463 */       resetOnError();
/*  464 */       logger.log(Level.FINE, "next() exception", e);
/*  465 */       throw new XMLStreamException(e);
/*  466 */     } catch (FastInfosetException e) {
/*  467 */       resetOnError();
/*  468 */       logger.log(Level.FINE, "next() exception", (Throwable)e);
/*  469 */       throw new XMLStreamException(e);
/*  470 */     } catch (RuntimeException e) {
/*  471 */       resetOnError();
/*  472 */       logger.log(Level.FINE, "next() exception", e);
/*  473 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   private final void processUtf8CharacterString(int b) throws IOException {
/*  478 */     if ((b & 0x10) > 0) {
/*  479 */       this._characterContentChunkTable.ensureSize(this._octetBufferLength);
/*  480 */       this._characters = this._characterContentChunkTable._array;
/*  481 */       this._charactersOffset = this._characterContentChunkTable._arrayIndex;
/*  482 */       decodeUtf8StringAsCharBuffer(this._characterContentChunkTable._array, this._charactersOffset);
/*  483 */       this._characterContentChunkTable.add(this._charBufferLength);
/*      */     } else {
/*  485 */       decodeUtf8StringAsCharBuffer();
/*  486 */       this._characters = this._charBuffer;
/*  487 */       this._charactersOffset = 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   private final void processUtf16CharacterString(int b) throws IOException {
/*  492 */     decodeUtf16StringAsCharBuffer();
/*  493 */     if ((b & 0x10) > 0) {
/*  494 */       this._charactersOffset = this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*  495 */       this._characters = this._characterContentChunkTable._array;
/*      */     } else {
/*  497 */       this._characters = this._charBuffer;
/*  498 */       this._charactersOffset = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void popStack() {
/*  504 */     this._qualifiedName = this._qNameStack[this._stackCount];
/*  505 */     this._currentNamespaceAIIsStart = this._namespaceAIIsStartStack[this._stackCount];
/*  506 */     this._currentNamespaceAIIsEnd = this._namespaceAIIsEndStack[this._stackCount];
/*  507 */     this._qNameStack[this._stackCount--] = null;
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
/*      */   public final void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/*  519 */     if (type != this._eventType)
/*  520 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.eventTypeNotMatch", new Object[] { getEventTypeString(type) })); 
/*  521 */     if (namespaceURI != null && !namespaceURI.equals(getNamespaceURI()))
/*  522 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.namespaceURINotMatch", new Object[] { namespaceURI })); 
/*  523 */     if (localName != null && !localName.equals(getLocalName())) {
/*  524 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.localNameNotMatch", new Object[] { localName }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getElementText() throws XMLStreamException {
/*  535 */     if (getEventType() != 1) {
/*  536 */       throw new XMLStreamException(
/*  537 */           CommonResourceBundle.getInstance().getString("message.mustBeOnSTARTELEMENT"), getLocation());
/*      */     }
/*      */     
/*  540 */     next();
/*  541 */     return getElementText(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getElementText(boolean startElementRead) throws XMLStreamException {
/*  547 */     if (!startElementRead) {
/*  548 */       throw new XMLStreamException(
/*  549 */           CommonResourceBundle.getInstance().getString("message.mustBeOnSTARTELEMENT"), getLocation());
/*      */     }
/*  551 */     int eventType = getEventType();
/*  552 */     StringBuilder content = new StringBuilder();
/*  553 */     while (eventType != 2) {
/*  554 */       if (eventType == 4 || eventType == 12 || eventType == 6 || eventType == 9) {
/*      */ 
/*      */ 
/*      */         
/*  558 */         content.append(getText());
/*  559 */       } else if (eventType != 3 && eventType != 5) {
/*      */ 
/*      */         
/*  562 */         if (eventType == 8)
/*  563 */           throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.unexpectedEOF")); 
/*  564 */         if (eventType == 1) {
/*  565 */           throw new XMLStreamException(
/*  566 */               CommonResourceBundle.getInstance().getString("message.getElementTextExpectTextOnly"), getLocation());
/*      */         }
/*  568 */         throw new XMLStreamException(
/*  569 */             CommonResourceBundle.getInstance().getString("message.unexpectedEventType") + getEventTypeString(eventType), getLocation());
/*      */       } 
/*  571 */       eventType = next();
/*      */     } 
/*  573 */     return content.toString();
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
/*      */   public final int nextTag() throws XMLStreamException {
/*  590 */     next();
/*  591 */     return nextTag(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int nextTag(boolean currentTagRead) throws XMLStreamException {
/*  597 */     int eventType = getEventType();
/*  598 */     if (!currentTagRead) {
/*  599 */       eventType = next();
/*      */     }
/*  601 */     while ((eventType == 4 && isWhiteSpace()) || (eventType == 12 && 
/*  602 */       isWhiteSpace()) || eventType == 6 || eventType == 3 || eventType == 5)
/*      */     {
/*      */ 
/*      */       
/*  606 */       eventType = next();
/*      */     }
/*  608 */     if (eventType != 1 && eventType != 2) {
/*  609 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.expectedStartOrEnd"), getLocation());
/*      */     }
/*  611 */     return eventType;
/*      */   }
/*      */   
/*      */   public final boolean hasNext() throws XMLStreamException {
/*  615 */     return (this._eventType != 8);
/*      */   }
/*      */   
/*      */   public void close() throws XMLStreamException {
/*      */     try {
/*  620 */       closeIfRequired();
/*  621 */     } catch (IOException iOException) {}
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getNamespaceURI(String prefix) {
/*  626 */     String namespace = getNamespaceDecl(prefix);
/*  627 */     if (namespace == null) {
/*  628 */       if (prefix == null) {
/*  629 */         throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.nullPrefix"));
/*      */       }
/*  631 */       return null;
/*      */     } 
/*  633 */     return namespace;
/*      */   }
/*      */   
/*      */   public final boolean isStartElement() {
/*  637 */     return (this._eventType == 1);
/*      */   }
/*      */   
/*      */   public final boolean isEndElement() {
/*  641 */     return (this._eventType == 2);
/*      */   }
/*      */   
/*      */   public final boolean isCharacters() {
/*  645 */     return (this._eventType == 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isWhiteSpace() {
/*  655 */     if (isCharacters() || this._eventType == 12) {
/*  656 */       char[] ch = getTextCharacters();
/*  657 */       int start = getTextStart();
/*  658 */       int length = getTextLength();
/*  659 */       for (int i = start; i < start + length; i++) {
/*  660 */         if (!XMLChar.isSpace(ch[i])) {
/*  661 */           return false;
/*      */         }
/*      */       } 
/*  664 */       return true;
/*      */     } 
/*  666 */     return false;
/*      */   }
/*      */   
/*      */   public final String getAttributeValue(String namespaceURI, String localName) {
/*  670 */     if (this._eventType != 1) {
/*  671 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*      */     
/*  674 */     if (localName == null) {
/*  675 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  678 */     if (namespaceURI != null) {
/*  679 */       for (int i = 0; i < this._attributes.getLength(); i++) {
/*  680 */         if (this._attributes.getLocalName(i).equals(localName) && this._attributes
/*  681 */           .getURI(i).equals(namespaceURI)) {
/*  682 */           return this._attributes.getValue(i);
/*      */         }
/*      */       } 
/*      */     } else {
/*  686 */       for (int i = 0; i < this._attributes.getLength(); i++) {
/*  687 */         if (this._attributes.getLocalName(i).equals(localName)) {
/*  688 */           return this._attributes.getValue(i);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  693 */     return null;
/*      */   }
/*      */   
/*      */   public final int getAttributeCount() {
/*  697 */     if (this._eventType != 1) {
/*  698 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*      */     
/*  701 */     return this._attributes.getLength();
/*      */   }
/*      */   
/*      */   public final QName getAttributeName(int index) {
/*  705 */     if (this._eventType != 1) {
/*  706 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  708 */     return this._attributes.getQualifiedName(index).getQName();
/*      */   }
/*      */   
/*      */   public final String getAttributeNamespace(int index) {
/*  712 */     if (this._eventType != 1) {
/*  713 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*      */     
/*  716 */     return this._attributes.getURI(index);
/*      */   }
/*      */   
/*      */   public final String getAttributeLocalName(int index) {
/*  720 */     if (this._eventType != 1) {
/*  721 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  723 */     return this._attributes.getLocalName(index);
/*      */   }
/*      */   
/*      */   public final String getAttributePrefix(int index) {
/*  727 */     if (this._eventType != 1) {
/*  728 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  730 */     return this._attributes.getPrefix(index);
/*      */   }
/*      */   
/*      */   public final String getAttributeType(int index) {
/*  734 */     if (this._eventType != 1) {
/*  735 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  737 */     return this._attributes.getType(index);
/*      */   }
/*      */   
/*      */   public final String getAttributeValue(int index) {
/*  741 */     if (this._eventType != 1) {
/*  742 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  744 */     return this._attributes.getValue(index);
/*      */   }
/*      */   
/*      */   public final boolean isAttributeSpecified(int index) {
/*  748 */     return false;
/*      */   }
/*      */   
/*      */   public final int getNamespaceCount() {
/*  752 */     if (this._eventType == 1 || this._eventType == 2) {
/*  753 */       return (this._currentNamespaceAIIsEnd > 0) ? (this._currentNamespaceAIIsEnd - this._currentNamespaceAIIsStart) : 0;
/*      */     }
/*  755 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetNamespaceCount"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getNamespacePrefix(int index) {
/*  760 */     if (this._eventType == 1 || this._eventType == 2) {
/*  761 */       return this._namespaceAIIsPrefix[this._currentNamespaceAIIsStart + index];
/*      */     }
/*  763 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetNamespacePrefix"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getNamespaceURI(int index) {
/*  768 */     if (this._eventType == 1 || this._eventType == 2) {
/*  769 */       return this._namespaceAIIsNamespaceName[this._currentNamespaceAIIsStart + index];
/*      */     }
/*  771 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetNamespacePrefix"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final NamespaceContext getNamespaceContext() {
/*  776 */     return this._nsContext;
/*      */   }
/*      */   
/*      */   public final int getEventType() {
/*  780 */     return this._eventType;
/*      */   }
/*      */   
/*      */   public final String getText() {
/*  784 */     if (this._characters == null) {
/*  785 */       checkTextState();
/*      */     }
/*      */     
/*  788 */     if (this._characters == this._characterContentChunkTable._array) {
/*  789 */       return this._characterContentChunkTable.getString(this._characterContentChunkTable._cachedIndex);
/*      */     }
/*  791 */     return new String(this._characters, this._charactersOffset, this._charBufferLength);
/*      */   }
/*      */ 
/*      */   
/*      */   public final char[] getTextCharacters() {
/*  796 */     if (this._characters == null) {
/*  797 */       checkTextState();
/*      */     }
/*      */     
/*  800 */     return this._characters;
/*      */   }
/*      */   
/*      */   public final int getTextStart() {
/*  804 */     if (this._characters == null) {
/*  805 */       checkTextState();
/*      */     }
/*      */     
/*  808 */     return this._charactersOffset;
/*      */   }
/*      */   
/*      */   public final int getTextLength() {
/*  812 */     if (this._characters == null) {
/*  813 */       checkTextState();
/*      */     }
/*      */     
/*  816 */     return this._charBufferLength;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/*  821 */     if (this._characters == null) {
/*  822 */       checkTextState();
/*      */     }
/*      */     
/*      */     try {
/*  826 */       int bytesToCopy = Math.min(this._charBufferLength, length);
/*  827 */       System.arraycopy(this._characters, this._charactersOffset + sourceStart, target, targetStart, bytesToCopy);
/*      */       
/*  829 */       return bytesToCopy;
/*  830 */     } catch (IndexOutOfBoundsException e) {
/*  831 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void checkTextState() {
/*  836 */     if (this._algorithmData == null) {
/*  837 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.InvalidStateForText"));
/*      */     }
/*      */     
/*      */     try {
/*  841 */       convertEncodingAlgorithmDataToCharacters();
/*  842 */     } catch (Exception e) {
/*  843 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.InvalidStateForText"));
/*      */     } 
/*      */   }
/*      */   
/*      */   public final String getEncoding() {
/*  848 */     return this._characterEncodingScheme;
/*      */   }
/*      */   
/*      */   public final boolean hasText() {
/*  852 */     return (this._characters != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final Location getLocation() {
/*  858 */     return EventLocation.getNilLocation();
/*      */   }
/*      */   
/*      */   public final QName getName() {
/*  862 */     if (this._eventType == 1 || this._eventType == 2) {
/*  863 */       return this._qualifiedName.getQName();
/*      */     }
/*  865 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetName"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getLocalName() {
/*  870 */     if (this._eventType == 1 || this._eventType == 2) {
/*  871 */       return this._qualifiedName.localName;
/*      */     }
/*  873 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetLocalName"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean hasName() {
/*  878 */     return (this._eventType == 1 || this._eventType == 2);
/*      */   }
/*      */   
/*      */   public final String getNamespaceURI() {
/*  882 */     if (this._eventType == 1 || this._eventType == 2) {
/*  883 */       return this._qualifiedName.namespaceName;
/*      */     }
/*  885 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetNamespaceURI"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getPrefix() {
/*  890 */     if (this._eventType == 1 || this._eventType == 2) {
/*  891 */       return this._qualifiedName.prefix;
/*      */     }
/*  893 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetPrefix"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getVersion() {
/*  898 */     return null;
/*      */   }
/*      */   
/*      */   public final boolean isStandalone() {
/*  902 */     return false;
/*      */   }
/*      */   
/*      */   public final boolean standaloneSet() {
/*  906 */     return false;
/*      */   }
/*      */   
/*      */   public final String getCharacterEncodingScheme() {
/*  910 */     return null;
/*      */   }
/*      */   
/*      */   public final String getPITarget() {
/*  914 */     if (this._eventType != 3) {
/*  915 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetPITarget"));
/*      */     }
/*      */     
/*  918 */     return this._piTarget;
/*      */   }
/*      */   
/*      */   public final String getPIData() {
/*  922 */     if (this._eventType != 3) {
/*  923 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetPIData"));
/*      */     }
/*      */     
/*  926 */     return this._piData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getNameString() {
/*  933 */     if (this._eventType == 1 || this._eventType == 2) {
/*  934 */       return this._qualifiedName.getQNameString();
/*      */     }
/*  936 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetName"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getAttributeNameString(int index) {
/*  941 */     if (this._eventType != 1) {
/*  942 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  944 */     return this._attributes.getQualifiedName(index).getQNameString();
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getTextAlgorithmURI() {
/*  949 */     return this._algorithmURI;
/*      */   }
/*      */   
/*      */   public final int getTextAlgorithmIndex() {
/*  953 */     return this._algorithmId;
/*      */   }
/*      */   
/*      */   public final boolean hasTextAlgorithmBytes() {
/*  957 */     return (this._algorithmData != null);
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
/*      */   public final byte[] getTextAlgorithmBytes() {
/*  970 */     if (this._algorithmData == null) {
/*  971 */       return null;
/*      */     }
/*      */     
/*  974 */     byte[] algorithmData = new byte[this._algorithmData.length];
/*  975 */     System.arraycopy(this._algorithmData, 0, algorithmData, 0, this._algorithmData.length);
/*  976 */     return algorithmData;
/*      */   }
/*      */   
/*      */   public final byte[] getTextAlgorithmBytesClone() {
/*  980 */     if (this._algorithmData == null) {
/*  981 */       return null;
/*      */     }
/*      */     
/*  984 */     byte[] algorithmData = new byte[this._algorithmDataLength];
/*  985 */     System.arraycopy(this._algorithmData, this._algorithmDataOffset, algorithmData, 0, this._algorithmDataLength);
/*  986 */     return algorithmData;
/*      */   }
/*      */   
/*      */   public final int getTextAlgorithmStart() {
/*  990 */     return this._algorithmDataOffset;
/*      */   }
/*      */   
/*      */   public final int getTextAlgorithmLength() {
/*  994 */     return this._algorithmDataLength;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int getTextAlgorithmBytes(int sourceStart, byte[] target, int targetStart, int length) throws XMLStreamException {
/*      */     try {
/* 1000 */       System.arraycopy(this._algorithmData, sourceStart, target, targetStart, length);
/*      */       
/* 1002 */       return length;
/* 1003 */     } catch (IndexOutOfBoundsException e) {
/* 1004 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int peekNext() throws XMLStreamException {
/*      */     try {
/* 1012 */       switch (DecoderStateTables.EII(peek(this))) {
/*      */         case 0:
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/* 1019 */           return 1;
/*      */         case 6:
/*      */         case 7:
/*      */         case 8:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 13:
/*      */         case 14:
/*      */         case 15:
/*      */         case 16:
/*      */         case 17:
/* 1032 */           return 4;
/*      */         case 18:
/* 1034 */           return 5;
/*      */         case 19:
/* 1036 */           return 3;
/*      */         case 21:
/* 1038 */           return 9;
/*      */         case 22:
/*      */         case 23:
/* 1041 */           return (this._stackCount != -1) ? 2 : 8;
/*      */       } 
/* 1043 */       throw new FastInfosetException(
/* 1044 */           CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     }
/* 1046 */     catch (IOException e) {
/* 1047 */       throw new XMLStreamException(e);
/* 1048 */     } catch (FastInfosetException e) {
/* 1049 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onBeforeOctetBufferOverwrite() {
/* 1054 */     if (this._algorithmData != null) {
/* 1055 */       this._algorithmData = getTextAlgorithmBytesClone();
/* 1056 */       this._algorithmDataOffset = 0;
/* 1057 */       this._isAlgorithmDataCloned = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int accessNamespaceCount() {
/* 1064 */     return (this._currentNamespaceAIIsEnd > 0) ? (this._currentNamespaceAIIsEnd - this._currentNamespaceAIIsStart) : 0;
/*      */   }
/*      */   
/*      */   public final String accessLocalName() {
/* 1068 */     return this._qualifiedName.localName;
/*      */   }
/*      */   
/*      */   public final String accessNamespaceURI() {
/* 1072 */     return this._qualifiedName.namespaceName;
/*      */   }
/*      */   
/*      */   public final String accessPrefix() {
/* 1076 */     return this._qualifiedName.prefix;
/*      */   }
/*      */   
/*      */   public final char[] accessTextCharacters() {
/* 1080 */     if (this._characters == null) return null;
/*      */ 
/*      */     
/* 1083 */     char[] clonedCharacters = new char[this._characters.length];
/* 1084 */     System.arraycopy(this._characters, 0, clonedCharacters, 0, this._characters.length);
/* 1085 */     return clonedCharacters;
/*      */   }
/*      */   
/*      */   public final int accessTextStart() {
/* 1089 */     return this._charactersOffset;
/*      */   }
/*      */   
/*      */   public final int accessTextLength() {
/* 1093 */     return this._charBufferLength;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processDII() throws FastInfosetException, IOException {
/* 1099 */     int b = read();
/* 1100 */     if (b > 0) {
/* 1101 */       processDIIOptionalProperties(b);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processDIIOptionalProperties(int b) throws FastInfosetException, IOException {
/* 1107 */     if (b == 32) {
/* 1108 */       decodeInitialVocabulary();
/*      */       
/*      */       return;
/*      */     } 
/* 1112 */     if ((b & 0x40) > 0) {
/* 1113 */       decodeAdditionalData();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1120 */     if ((b & 0x20) > 0) {
/* 1121 */       decodeInitialVocabulary();
/*      */     }
/*      */     
/* 1124 */     if ((b & 0x10) > 0) {
/* 1125 */       decodeNotations();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1135 */     if ((b & 0x8) > 0) {
/* 1136 */       decodeUnparsedEntities();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1146 */     if ((b & 0x4) > 0) {
/* 1147 */       this._characterEncodingScheme = decodeCharacterEncodingScheme();
/*      */     }
/*      */     
/* 1150 */     if ((b & 0x2) > 0) {
/* 1151 */       boolean bool = (read() > 0) ? true : false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1158 */     if ((b & 0x1) > 0) {
/* 1159 */       decodeVersion();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void resizeNamespaceAIIs() {
/* 1169 */     String[] namespaceAIIsPrefix = new String[this._namespaceAIIsIndex * 2];
/* 1170 */     System.arraycopy(this._namespaceAIIsPrefix, 0, namespaceAIIsPrefix, 0, this._namespaceAIIsIndex);
/* 1171 */     this._namespaceAIIsPrefix = namespaceAIIsPrefix;
/*      */     
/* 1173 */     String[] namespaceAIIsNamespaceName = new String[this._namespaceAIIsIndex * 2];
/* 1174 */     System.arraycopy(this._namespaceAIIsNamespaceName, 0, namespaceAIIsNamespaceName, 0, this._namespaceAIIsIndex);
/* 1175 */     this._namespaceAIIsNamespaceName = namespaceAIIsNamespaceName;
/*      */     
/* 1177 */     int[] namespaceAIIsPrefixIndex = new int[this._namespaceAIIsIndex * 2];
/* 1178 */     System.arraycopy(this._namespaceAIIsPrefixIndex, 0, namespaceAIIsPrefixIndex, 0, this._namespaceAIIsIndex);
/* 1179 */     this._namespaceAIIsPrefixIndex = namespaceAIIsPrefixIndex;
/*      */   }
/*      */   protected final void processEIIWithNamespaces(boolean hasAttributes) throws FastInfosetException, IOException {
/*      */     QualifiedName qn;
/* 1183 */     if (++this._prefixTable._declarationId == Integer.MAX_VALUE) {
/* 1184 */       this._prefixTable.clearDeclarationIds();
/*      */     }
/*      */     
/* 1187 */     this._currentNamespaceAIIsStart = this._namespaceAIIsIndex;
/* 1188 */     String prefix = "", namespaceName = "";
/* 1189 */     int b = read();
/* 1190 */     while ((b & 0xFC) == 204) {
/* 1191 */       if (this._namespaceAIIsIndex == this._namespaceAIIsPrefix.length) {
/* 1192 */         resizeNamespaceAIIs();
/*      */       }
/*      */       
/* 1195 */       switch (b & 0x3) {
/*      */ 
/*      */         
/*      */         case 0:
/* 1199 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsIndex] = ""; this._namespaceAIIsPrefix[this._namespaceAIIsIndex] = ""; prefix = namespaceName = "";
/*      */ 
/*      */ 
/*      */           
/* 1203 */           this._namespaceAIIsPrefixIndex[this._namespaceAIIsIndex++] = -1; this._namespaceNameIndex = this._prefixIndex = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 1:
/* 1208 */           prefix = this._namespaceAIIsPrefix[this._namespaceAIIsIndex] = "";
/*      */           
/* 1210 */           namespaceName = this._namespaceAIIsNamespaceName[this._namespaceAIIsIndex] = decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(false);
/*      */           
/* 1212 */           this._prefixIndex = this._namespaceAIIsPrefixIndex[this._namespaceAIIsIndex++] = -1;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1218 */           prefix = this._namespaceAIIsPrefix[this._namespaceAIIsIndex] = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(false);
/* 1219 */           namespaceName = this._namespaceAIIsNamespaceName[this._namespaceAIIsIndex] = "";
/*      */           
/* 1221 */           this._namespaceNameIndex = -1;
/* 1222 */           this._namespaceAIIsPrefixIndex[this._namespaceAIIsIndex++] = this._prefixIndex;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 3:
/* 1228 */           prefix = this._namespaceAIIsPrefix[this._namespaceAIIsIndex] = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(true);
/*      */           
/* 1230 */           namespaceName = this._namespaceAIIsNamespaceName[this._namespaceAIIsIndex] = decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(true);
/*      */           
/* 1232 */           this._namespaceAIIsPrefixIndex[this._namespaceAIIsIndex++] = this._prefixIndex;
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1237 */       this._prefixTable.pushScopeWithPrefixEntry(prefix, namespaceName, this._prefixIndex, this._namespaceNameIndex);
/*      */       
/* 1239 */       b = read();
/*      */     } 
/* 1241 */     if (b != 240) {
/* 1242 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.EIInamespaceNameNotTerminatedCorrectly"));
/*      */     }
/* 1244 */     this._currentNamespaceAIIsEnd = this._namespaceAIIsIndex;
/*      */     
/* 1246 */     b = read();
/* 1247 */     switch (DecoderStateTables.EII(b)) {
/*      */       case 0:
/* 1249 */         processEII(this._elementNameTable._array[b], hasAttributes);
/*      */         return;
/*      */       case 2:
/* 1252 */         processEII(processEIIIndexMedium(b), hasAttributes);
/*      */         return;
/*      */       case 3:
/* 1255 */         processEII(processEIIIndexLarge(b), hasAttributes);
/*      */         return;
/*      */       
/*      */       case 5:
/* 1259 */         qn = processLiteralQualifiedName(b & 0x3, this._elementNameTable
/*      */             
/* 1261 */             .getNext());
/* 1262 */         this._elementNameTable.add(qn);
/* 1263 */         processEII(qn, hasAttributes);
/*      */         return;
/*      */     } 
/*      */     
/* 1267 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEIIAfterAIIs"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processEII(QualifiedName name, boolean hasAttributes) throws FastInfosetException, IOException {
/* 1272 */     if (this._prefixTable._currentInScope[name.prefixIndex] != name.namespaceNameIndex) {
/* 1273 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qnameOfEIINotInScope"));
/*      */     }
/*      */     
/* 1276 */     this._eventType = 1;
/* 1277 */     this._qualifiedName = name;
/*      */     
/* 1279 */     if (this._clearAttributes) {
/* 1280 */       this._attributes.clear();
/* 1281 */       this._clearAttributes = false;
/*      */     } 
/*      */     
/* 1284 */     if (hasAttributes) {
/* 1285 */       processAIIs();
/*      */     }
/*      */ 
/*      */     
/* 1289 */     this._stackCount++;
/* 1290 */     if (this._stackCount == this._qNameStack.length) {
/* 1291 */       QualifiedName[] qNameStack = new QualifiedName[this._qNameStack.length * 2];
/* 1292 */       System.arraycopy(this._qNameStack, 0, qNameStack, 0, this._qNameStack.length);
/* 1293 */       this._qNameStack = qNameStack;
/*      */       
/* 1295 */       int[] namespaceAIIsStartStack = new int[this._namespaceAIIsStartStack.length * 2];
/* 1296 */       System.arraycopy(this._namespaceAIIsStartStack, 0, namespaceAIIsStartStack, 0, this._namespaceAIIsStartStack.length);
/* 1297 */       this._namespaceAIIsStartStack = namespaceAIIsStartStack;
/*      */       
/* 1299 */       int[] namespaceAIIsEndStack = new int[this._namespaceAIIsEndStack.length * 2];
/* 1300 */       System.arraycopy(this._namespaceAIIsEndStack, 0, namespaceAIIsEndStack, 0, this._namespaceAIIsEndStack.length);
/* 1301 */       this._namespaceAIIsEndStack = namespaceAIIsEndStack;
/*      */     } 
/* 1303 */     this._qNameStack[this._stackCount] = this._qualifiedName;
/* 1304 */     this._namespaceAIIsStartStack[this._stackCount] = this._currentNamespaceAIIsStart;
/* 1305 */     this._namespaceAIIsEndStack[this._stackCount] = this._currentNamespaceAIIsEnd;
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
/*      */   protected final void processAIIs() throws FastInfosetException, IOException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   4: dup
/*      */     //   5: getfield _currentIteration : I
/*      */     //   8: iconst_1
/*      */     //   9: iadd
/*      */     //   10: dup_x1
/*      */     //   11: putfield _currentIteration : I
/*      */     //   14: ldc 2147483647
/*      */     //   16: if_icmpne -> 26
/*      */     //   19: aload_0
/*      */     //   20: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   23: invokevirtual clear : ()V
/*      */     //   26: aload_0
/*      */     //   27: iconst_1
/*      */     //   28: putfield _clearAttributes : Z
/*      */     //   31: iconst_0
/*      */     //   32: istore #4
/*      */     //   34: aload_0
/*      */     //   35: invokevirtual read : ()I
/*      */     //   38: istore_2
/*      */     //   39: iload_2
/*      */     //   40: invokestatic AII : (I)I
/*      */     //   43: tableswitch default -> 208, 0 -> 80, 1 -> 93, 2 -> 124, 3 -> 164, 4 -> 202, 5 -> 197
/*      */     //   80: aload_0
/*      */     //   81: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   84: getfield _array : [Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   87: iload_2
/*      */     //   88: aaload
/*      */     //   89: astore_1
/*      */     //   90: goto -> 224
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
/*      */     //   108: istore #5
/*      */     //   110: aload_0
/*      */     //   111: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   114: getfield _array : [Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   117: iload #5
/*      */     //   119: aaload
/*      */     //   120: astore_1
/*      */     //   121: goto -> 224
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
/*      */     //   148: istore #5
/*      */     //   150: aload_0
/*      */     //   151: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   154: getfield _array : [Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   157: iload #5
/*      */     //   159: aaload
/*      */     //   160: astore_1
/*      */     //   161: goto -> 224
/*      */     //   164: aload_0
/*      */     //   165: iload_2
/*      */     //   166: iconst_3
/*      */     //   167: iand
/*      */     //   168: aload_0
/*      */     //   169: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   172: invokevirtual getNext : ()Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   175: invokevirtual processLiteralQualifiedName : (ILcom/sun/xml/internal/fastinfoset/QualifiedName;)Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   178: astore_1
/*      */     //   179: aload_1
/*      */     //   180: sipush #256
/*      */     //   183: invokevirtual createAttributeValues : (I)V
/*      */     //   186: aload_0
/*      */     //   187: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   190: aload_1
/*      */     //   191: invokevirtual add : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;)V
/*      */     //   194: goto -> 224
/*      */     //   197: aload_0
/*      */     //   198: iconst_1
/*      */     //   199: putfield _internalState : I
/*      */     //   202: iconst_1
/*      */     //   203: istore #4
/*      */     //   205: goto -> 942
/*      */     //   208: new com/sun/xml/internal/org/jvnet/fastinfoset/FastInfosetException
/*      */     //   211: dup
/*      */     //   212: invokestatic getInstance : ()Lcom/sun/xml/internal/fastinfoset/CommonResourceBundle;
/*      */     //   215: ldc 'message.decodingAIIs'
/*      */     //   217: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   220: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   223: athrow
/*      */     //   224: aload_1
/*      */     //   225: getfield prefixIndex : I
/*      */     //   228: ifle -> 266
/*      */     //   231: aload_0
/*      */     //   232: getfield _prefixTable : Lcom/sun/xml/internal/fastinfoset/util/PrefixArray;
/*      */     //   235: getfield _currentInScope : [I
/*      */     //   238: aload_1
/*      */     //   239: getfield prefixIndex : I
/*      */     //   242: iaload
/*      */     //   243: aload_1
/*      */     //   244: getfield namespaceNameIndex : I
/*      */     //   247: if_icmpeq -> 266
/*      */     //   250: new com/sun/xml/internal/org/jvnet/fastinfoset/FastInfosetException
/*      */     //   253: dup
/*      */     //   254: invokestatic getInstance : ()Lcom/sun/xml/internal/fastinfoset/CommonResourceBundle;
/*      */     //   257: ldc 'message.AIIqNameNotInScope'
/*      */     //   259: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   262: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   265: athrow
/*      */     //   266: aload_0
/*      */     //   267: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   270: aload_1
/*      */     //   271: getfield attributeHash : I
/*      */     //   274: aload_1
/*      */     //   275: getfield attributeId : I
/*      */     //   278: invokevirtual checkForDuplicateAttribute : (II)V
/*      */     //   281: aload_0
/*      */     //   282: invokevirtual read : ()I
/*      */     //   285: istore_2
/*      */     //   286: iload_2
/*      */     //   287: invokestatic NISTRING : (I)I
/*      */     //   290: tableswitch default -> 926, 0 -> 352, 1 -> 395, 2 -> 439, 3 -> 508, 4 -> 551, 5 -> 595, 6 -> 664, 7 -> 745, 8 -> 805, 9 -> 828, 10 -> 866, 11 -> 913
/*      */     //   352: aload_0
/*      */     //   353: iload_2
/*      */     //   354: bipush #7
/*      */     //   356: iand
/*      */     //   357: iconst_1
/*      */     //   358: iadd
/*      */     //   359: putfield _octetBufferLength : I
/*      */     //   362: aload_0
/*      */     //   363: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   366: astore_3
/*      */     //   367: iload_2
/*      */     //   368: bipush #64
/*      */     //   370: iand
/*      */     //   371: ifle -> 383
/*      */     //   374: aload_0
/*      */     //   375: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   378: aload_3
/*      */     //   379: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   382: pop
/*      */     //   383: aload_0
/*      */     //   384: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   387: aload_1
/*      */     //   388: aload_3
/*      */     //   389: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   392: goto -> 942
/*      */     //   395: aload_0
/*      */     //   396: aload_0
/*      */     //   397: invokevirtual read : ()I
/*      */     //   400: bipush #9
/*      */     //   402: iadd
/*      */     //   403: putfield _octetBufferLength : I
/*      */     //   406: aload_0
/*      */     //   407: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   410: astore_3
/*      */     //   411: iload_2
/*      */     //   412: bipush #64
/*      */     //   414: iand
/*      */     //   415: ifle -> 427
/*      */     //   418: aload_0
/*      */     //   419: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   422: aload_3
/*      */     //   423: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   426: pop
/*      */     //   427: aload_0
/*      */     //   428: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   431: aload_1
/*      */     //   432: aload_3
/*      */     //   433: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   436: goto -> 942
/*      */     //   439: aload_0
/*      */     //   440: aload_0
/*      */     //   441: invokevirtual read : ()I
/*      */     //   444: bipush #24
/*      */     //   446: ishl
/*      */     //   447: aload_0
/*      */     //   448: invokevirtual read : ()I
/*      */     //   451: bipush #16
/*      */     //   453: ishl
/*      */     //   454: ior
/*      */     //   455: aload_0
/*      */     //   456: invokevirtual read : ()I
/*      */     //   459: bipush #8
/*      */     //   461: ishl
/*      */     //   462: ior
/*      */     //   463: aload_0
/*      */     //   464: invokevirtual read : ()I
/*      */     //   467: ior
/*      */     //   468: sipush #265
/*      */     //   471: iadd
/*      */     //   472: putfield _octetBufferLength : I
/*      */     //   475: aload_0
/*      */     //   476: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   479: astore_3
/*      */     //   480: iload_2
/*      */     //   481: bipush #64
/*      */     //   483: iand
/*      */     //   484: ifle -> 496
/*      */     //   487: aload_0
/*      */     //   488: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   491: aload_3
/*      */     //   492: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   495: pop
/*      */     //   496: aload_0
/*      */     //   497: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   500: aload_1
/*      */     //   501: aload_3
/*      */     //   502: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   505: goto -> 942
/*      */     //   508: aload_0
/*      */     //   509: iload_2
/*      */     //   510: bipush #7
/*      */     //   512: iand
/*      */     //   513: iconst_1
/*      */     //   514: iadd
/*      */     //   515: putfield _octetBufferLength : I
/*      */     //   518: aload_0
/*      */     //   519: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   522: astore_3
/*      */     //   523: iload_2
/*      */     //   524: bipush #64
/*      */     //   526: iand
/*      */     //   527: ifle -> 539
/*      */     //   530: aload_0
/*      */     //   531: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   534: aload_3
/*      */     //   535: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   538: pop
/*      */     //   539: aload_0
/*      */     //   540: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   543: aload_1
/*      */     //   544: aload_3
/*      */     //   545: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   548: goto -> 942
/*      */     //   551: aload_0
/*      */     //   552: aload_0
/*      */     //   553: invokevirtual read : ()I
/*      */     //   556: bipush #9
/*      */     //   558: iadd
/*      */     //   559: putfield _octetBufferLength : I
/*      */     //   562: aload_0
/*      */     //   563: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   566: astore_3
/*      */     //   567: iload_2
/*      */     //   568: bipush #64
/*      */     //   570: iand
/*      */     //   571: ifle -> 583
/*      */     //   574: aload_0
/*      */     //   575: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   578: aload_3
/*      */     //   579: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   582: pop
/*      */     //   583: aload_0
/*      */     //   584: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   587: aload_1
/*      */     //   588: aload_3
/*      */     //   589: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   592: goto -> 942
/*      */     //   595: aload_0
/*      */     //   596: aload_0
/*      */     //   597: invokevirtual read : ()I
/*      */     //   600: bipush #24
/*      */     //   602: ishl
/*      */     //   603: aload_0
/*      */     //   604: invokevirtual read : ()I
/*      */     //   607: bipush #16
/*      */     //   609: ishl
/*      */     //   610: ior
/*      */     //   611: aload_0
/*      */     //   612: invokevirtual read : ()I
/*      */     //   615: bipush #8
/*      */     //   617: ishl
/*      */     //   618: ior
/*      */     //   619: aload_0
/*      */     //   620: invokevirtual read : ()I
/*      */     //   623: ior
/*      */     //   624: sipush #265
/*      */     //   627: iadd
/*      */     //   628: putfield _octetBufferLength : I
/*      */     //   631: aload_0
/*      */     //   632: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   635: astore_3
/*      */     //   636: iload_2
/*      */     //   637: bipush #64
/*      */     //   639: iand
/*      */     //   640: ifle -> 652
/*      */     //   643: aload_0
/*      */     //   644: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   647: aload_3
/*      */     //   648: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   651: pop
/*      */     //   652: aload_0
/*      */     //   653: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   656: aload_1
/*      */     //   657: aload_3
/*      */     //   658: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   661: goto -> 942
/*      */     //   664: iload_2
/*      */     //   665: bipush #64
/*      */     //   667: iand
/*      */     //   668: ifle -> 675
/*      */     //   671: iconst_1
/*      */     //   672: goto -> 676
/*      */     //   675: iconst_0
/*      */     //   676: istore #5
/*      */     //   678: aload_0
/*      */     //   679: iload_2
/*      */     //   680: bipush #15
/*      */     //   682: iand
/*      */     //   683: iconst_4
/*      */     //   684: ishl
/*      */     //   685: putfield _identifier : I
/*      */     //   688: aload_0
/*      */     //   689: invokevirtual read : ()I
/*      */     //   692: istore_2
/*      */     //   693: aload_0
/*      */     //   694: dup
/*      */     //   695: getfield _identifier : I
/*      */     //   698: iload_2
/*      */     //   699: sipush #240
/*      */     //   702: iand
/*      */     //   703: iconst_4
/*      */     //   704: ishr
/*      */     //   705: ior
/*      */     //   706: putfield _identifier : I
/*      */     //   709: aload_0
/*      */     //   710: iload_2
/*      */     //   711: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   714: aload_0
/*      */     //   715: invokevirtual decodeRestrictedAlphabetAsString : ()Ljava/lang/String;
/*      */     //   718: astore_3
/*      */     //   719: iload #5
/*      */     //   721: ifeq -> 733
/*      */     //   724: aload_0
/*      */     //   725: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   728: aload_3
/*      */     //   729: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   732: pop
/*      */     //   733: aload_0
/*      */     //   734: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   737: aload_1
/*      */     //   738: aload_3
/*      */     //   739: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   742: goto -> 942
/*      */     //   745: iload_2
/*      */     //   746: bipush #64
/*      */     //   748: iand
/*      */     //   749: ifle -> 756
/*      */     //   752: iconst_1
/*      */     //   753: goto -> 757
/*      */     //   756: iconst_0
/*      */     //   757: istore #5
/*      */     //   759: aload_0
/*      */     //   760: iload_2
/*      */     //   761: bipush #15
/*      */     //   763: iand
/*      */     //   764: iconst_4
/*      */     //   765: ishl
/*      */     //   766: putfield _identifier : I
/*      */     //   769: aload_0
/*      */     //   770: invokevirtual read : ()I
/*      */     //   773: istore_2
/*      */     //   774: aload_0
/*      */     //   775: dup
/*      */     //   776: getfield _identifier : I
/*      */     //   779: iload_2
/*      */     //   780: sipush #240
/*      */     //   783: iand
/*      */     //   784: iconst_4
/*      */     //   785: ishr
/*      */     //   786: ior
/*      */     //   787: putfield _identifier : I
/*      */     //   790: aload_0
/*      */     //   791: iload_2
/*      */     //   792: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   795: aload_0
/*      */     //   796: aload_1
/*      */     //   797: iload #5
/*      */     //   799: invokevirtual processAIIEncodingAlgorithm : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Z)V
/*      */     //   802: goto -> 942
/*      */     //   805: aload_0
/*      */     //   806: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   809: aload_1
/*      */     //   810: aload_0
/*      */     //   811: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   814: getfield _array : [Ljava/lang/String;
/*      */     //   817: iload_2
/*      */     //   818: bipush #63
/*      */     //   820: iand
/*      */     //   821: aaload
/*      */     //   822: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   825: goto -> 942
/*      */     //   828: iload_2
/*      */     //   829: bipush #31
/*      */     //   831: iand
/*      */     //   832: bipush #8
/*      */     //   834: ishl
/*      */     //   835: aload_0
/*      */     //   836: invokevirtual read : ()I
/*      */     //   839: ior
/*      */     //   840: bipush #64
/*      */     //   842: iadd
/*      */     //   843: istore #5
/*      */     //   845: aload_0
/*      */     //   846: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   849: aload_1
/*      */     //   850: aload_0
/*      */     //   851: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   854: getfield _array : [Ljava/lang/String;
/*      */     //   857: iload #5
/*      */     //   859: aaload
/*      */     //   860: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   863: goto -> 942
/*      */     //   866: iload_2
/*      */     //   867: bipush #15
/*      */     //   869: iand
/*      */     //   870: bipush #16
/*      */     //   872: ishl
/*      */     //   873: aload_0
/*      */     //   874: invokevirtual read : ()I
/*      */     //   877: bipush #8
/*      */     //   879: ishl
/*      */     //   880: ior
/*      */     //   881: aload_0
/*      */     //   882: invokevirtual read : ()I
/*      */     //   885: ior
/*      */     //   886: sipush #8256
/*      */     //   889: iadd
/*      */     //   890: istore #5
/*      */     //   892: aload_0
/*      */     //   893: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   896: aload_1
/*      */     //   897: aload_0
/*      */     //   898: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   901: getfield _array : [Ljava/lang/String;
/*      */     //   904: iload #5
/*      */     //   906: aaload
/*      */     //   907: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   910: goto -> 942
/*      */     //   913: aload_0
/*      */     //   914: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   917: aload_1
/*      */     //   918: ldc ''
/*      */     //   920: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   923: goto -> 942
/*      */     //   926: new com/sun/xml/internal/org/jvnet/fastinfoset/FastInfosetException
/*      */     //   929: dup
/*      */     //   930: invokestatic getInstance : ()Lcom/sun/xml/internal/fastinfoset/CommonResourceBundle;
/*      */     //   933: ldc 'message.decodingAIIValue'
/*      */     //   935: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   938: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   941: athrow
/*      */     //   942: iload #4
/*      */     //   944: ifeq -> 34
/*      */     //   947: aload_0
/*      */     //   948: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   951: aload_0
/*      */     //   952: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   955: getfield _poolHead : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   958: putfield _poolCurrent : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   961: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1313	-> 0
/*      */     //   #1314	-> 19
/*      */     //   #1317	-> 26
/*      */     //   #1318	-> 31
/*      */     //   #1321	-> 34
/*      */     //   #1322	-> 39
/*      */     //   #1324	-> 80
/*      */     //   #1325	-> 90
/*      */     //   #1328	-> 93
/*      */     //   #1330	-> 110
/*      */     //   #1331	-> 121
/*      */     //   #1335	-> 124
/*      */     //   #1337	-> 150
/*      */     //   #1338	-> 161
/*      */     //   #1341	-> 164
/*      */     //   #1343	-> 172
/*      */     //   #1341	-> 175
/*      */     //   #1344	-> 179
/*      */     //   #1345	-> 186
/*      */     //   #1346	-> 194
/*      */     //   #1348	-> 197
/*      */     //   #1350	-> 202
/*      */     //   #1352	-> 205
/*      */     //   #1354	-> 208
/*      */     //   #1359	-> 224
/*      */     //   #1360	-> 250
/*      */     //   #1363	-> 266
/*      */     //   #1365	-> 281
/*      */     //   #1366	-> 286
/*      */     //   #1368	-> 352
/*      */     //   #1369	-> 362
/*      */     //   #1370	-> 367
/*      */     //   #1371	-> 374
/*      */     //   #1374	-> 383
/*      */     //   #1375	-> 392
/*      */     //   #1377	-> 395
/*      */     //   #1378	-> 406
/*      */     //   #1379	-> 411
/*      */     //   #1380	-> 418
/*      */     //   #1383	-> 427
/*      */     //   #1384	-> 436
/*      */     //   #1386	-> 439
/*      */     //   #1387	-> 448
/*      */     //   #1388	-> 456
/*      */     //   #1389	-> 464
/*      */     //   #1391	-> 475
/*      */     //   #1392	-> 480
/*      */     //   #1393	-> 487
/*      */     //   #1396	-> 496
/*      */     //   #1397	-> 505
/*      */     //   #1399	-> 508
/*      */     //   #1400	-> 518
/*      */     //   #1401	-> 523
/*      */     //   #1402	-> 530
/*      */     //   #1405	-> 539
/*      */     //   #1406	-> 548
/*      */     //   #1408	-> 551
/*      */     //   #1409	-> 562
/*      */     //   #1410	-> 567
/*      */     //   #1411	-> 574
/*      */     //   #1414	-> 583
/*      */     //   #1415	-> 592
/*      */     //   #1417	-> 595
/*      */     //   #1418	-> 604
/*      */     //   #1419	-> 612
/*      */     //   #1420	-> 620
/*      */     //   #1422	-> 631
/*      */     //   #1423	-> 636
/*      */     //   #1424	-> 643
/*      */     //   #1427	-> 652
/*      */     //   #1428	-> 661
/*      */     //   #1431	-> 664
/*      */     //   #1433	-> 678
/*      */     //   #1434	-> 688
/*      */     //   #1435	-> 693
/*      */     //   #1437	-> 709
/*      */     //   #1439	-> 714
/*      */     //   #1440	-> 719
/*      */     //   #1441	-> 724
/*      */     //   #1444	-> 733
/*      */     //   #1445	-> 742
/*      */     //   #1449	-> 745
/*      */     //   #1451	-> 759
/*      */     //   #1452	-> 769
/*      */     //   #1453	-> 774
/*      */     //   #1455	-> 790
/*      */     //   #1456	-> 795
/*      */     //   #1457	-> 802
/*      */     //   #1460	-> 805
/*      */     //   #1462	-> 825
/*      */     //   #1465	-> 828
/*      */     //   #1468	-> 845
/*      */     //   #1470	-> 863
/*      */     //   #1474	-> 866
/*      */     //   #1477	-> 892
/*      */     //   #1479	-> 910
/*      */     //   #1482	-> 913
/*      */     //   #1483	-> 923
/*      */     //   #1485	-> 926
/*      */     //   #1488	-> 942
/*      */     //   #1491	-> 947
/*      */     //   #1492	-> 961
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   90	3	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   110	14	5	i	I
/*      */     //   121	3	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   150	14	5	i	I
/*      */     //   161	3	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   179	18	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   367	28	3	value	Ljava/lang/String;
/*      */     //   411	28	3	value	Ljava/lang/String;
/*      */     //   480	28	3	value	Ljava/lang/String;
/*      */     //   523	28	3	value	Ljava/lang/String;
/*      */     //   567	28	3	value	Ljava/lang/String;
/*      */     //   636	28	3	value	Ljava/lang/String;
/*      */     //   678	67	5	addToTable	Z
/*      */     //   719	26	3	value	Ljava/lang/String;
/*      */     //   759	46	5	addToTable	Z
/*      */     //   845	21	5	index	I
/*      */     //   892	21	5	index	I
/*      */     //   224	718	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   0	962	0	this	Lcom/sun/xml/internal/fastinfoset/stax/StAXDocumentParser;
/*      */     //   39	923	2	b	I
/*      */     //   34	928	4	terminate	Z
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
/*      */   protected final QualifiedName processEIIIndexMedium(int b) throws FastInfosetException, IOException {
/* 1495 */     int i = ((b & 0x7) << 8 | read()) + 32;
/*      */     
/* 1497 */     return this._elementNameTable._array[i];
/*      */   }
/*      */   
/*      */   protected final QualifiedName processEIIIndexLarge(int b) throws FastInfosetException, IOException {
/*      */     int i;
/* 1502 */     if ((b & 0x30) == 32) {
/*      */       
/* 1504 */       i = ((b & 0x7) << 16 | read() << 8 | read()) + 2080;
/*      */     }
/*      */     else {
/*      */       
/* 1508 */       i = ((read() & 0xF) << 16 | read() << 8 | read()) + 526368;
/*      */     } 
/*      */     
/* 1511 */     return this._elementNameTable._array[i];
/*      */   }
/*      */ 
/*      */   
/*      */   protected final QualifiedName processLiteralQualifiedName(int state, QualifiedName q) throws FastInfosetException, IOException {
/* 1516 */     if (q == null) q = new QualifiedName();
/*      */     
/* 1518 */     switch (state) {
/*      */       
/*      */       case 0:
/* 1521 */         return q.set("", "", 
/*      */ 
/*      */             
/* 1524 */             decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), "", 0, -1, -1, this._identifier);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1532 */         return q.set("", 
/*      */             
/* 1534 */             decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(false), 
/* 1535 */             decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), "", 0, -1, this._namespaceNameIndex, this._identifier);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1543 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qNameMissingNamespaceName"));
/*      */       
/*      */       case 3:
/* 1546 */         return q.set(
/* 1547 */             decodeIdentifyingNonEmptyStringIndexOnFirstBitAsPrefix(true), 
/* 1548 */             decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(true), 
/* 1549 */             decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), "", 0, this._prefixIndex, this._namespaceNameIndex, this._identifier);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1556 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingEII"));
/*      */   }
/*      */   
/*      */   protected final void processCommentII() throws FastInfosetException, IOException {
/*      */     CharArray ca;
/* 1561 */     this._eventType = 5;
/*      */     
/* 1563 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       case 0:
/* 1565 */         if (this._addToTable) {
/* 1566 */           this._v.otherString.add(new CharArray(this._charBuffer, 0, this._charBufferLength, true));
/*      */         }
/*      */         
/* 1569 */         this._characters = this._charBuffer;
/* 1570 */         this._charactersOffset = 0;
/*      */         break;
/*      */       case 2:
/* 1573 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.commentIIAlgorithmNotSupported"));
/*      */       case 1:
/* 1575 */         ca = this._v.otherString.get(this._integer);
/*      */         
/* 1577 */         this._characters = ca.ch;
/* 1578 */         this._charactersOffset = ca.start;
/* 1579 */         this._charBufferLength = ca.length;
/*      */         break;
/*      */       case 3:
/* 1582 */         this._characters = this._charBuffer;
/* 1583 */         this._charactersOffset = 0;
/* 1584 */         this._charBufferLength = 0;
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processProcessingII() throws FastInfosetException, IOException {
/* 1590 */     this._eventType = 3;
/*      */     
/* 1592 */     this._piTarget = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */     
/* 1594 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       case 0:
/* 1596 */         this._piData = new String(this._charBuffer, 0, this._charBufferLength);
/* 1597 */         if (this._addToTable) {
/* 1598 */           this._v.otherString.add((CharArray)new CharArrayString(this._piData));
/*      */         }
/*      */         break;
/*      */       case 2:
/* 1602 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */       case 1:
/* 1604 */         this._piData = this._v.otherString.get(this._integer).toString();
/*      */         break;
/*      */       case 3:
/* 1607 */         this._piData = "";
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processUnexpandedEntityReference(int b) throws FastInfosetException, IOException {
/* 1613 */     this._eventType = 9;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1619 */     String entity_reference_name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */ 
/*      */     
/* 1622 */     String system_identifier = ((b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */     
/* 1624 */     String public_identifier = ((b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */     
/* 1626 */     if (logger.isLoggable(Level.FINEST)) {
/* 1627 */       logger.log(Level.FINEST, "processUnexpandedEntityReference: entity_reference_name={0} system_identifier={1}public_identifier={2}", new Object[] { entity_reference_name, system_identifier, public_identifier });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processCIIEncodingAlgorithm(boolean addToTable) throws FastInfosetException, IOException {
/* 1633 */     this._algorithmData = this._octetBuffer;
/* 1634 */     this._algorithmDataOffset = this._octetBufferStart;
/* 1635 */     this._algorithmDataLength = this._octetBufferLength;
/* 1636 */     this._isAlgorithmDataCloned = false;
/*      */     
/* 1638 */     if (this._algorithmId >= 32) {
/* 1639 */       this._algorithmURI = this._v.encodingAlgorithm.get(this._algorithmId - 32);
/* 1640 */       if (this._algorithmURI == null) {
/* 1641 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.URINotPresent", new Object[] { Integer.valueOf(this._identifier) }));
/*      */       }
/* 1643 */     } else if (this._algorithmId > 9) {
/*      */ 
/*      */ 
/*      */       
/* 1647 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
/*      */     } 
/*      */     
/* 1650 */     if (addToTable) {
/* 1651 */       convertEncodingAlgorithmDataToCharacters();
/* 1652 */       this._characterContentChunkTable.add(this._characters, this._characters.length);
/*      */     } 
/*      */   } protected final void processAIIEncodingAlgorithm(QualifiedName name, boolean addToTable) throws FastInfosetException, IOException {
/*      */     BuiltInEncodingAlgorithm builtInEncodingAlgorithm;
/*      */     Object algorithmData;
/* 1657 */     EncodingAlgorithm ea = null;
/* 1658 */     String URI = null;
/* 1659 */     if (this._identifier >= 32) {
/* 1660 */       URI = this._v.encodingAlgorithm.get(this._identifier - 32);
/* 1661 */       if (URI == null)
/* 1662 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.URINotPresent", new Object[] { Integer.valueOf(this._identifier) })); 
/* 1663 */       if (this._registeredEncodingAlgorithms != null)
/* 1664 */         ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI); 
/*      */     } else {
/* 1666 */       if (this._identifier >= 9) {
/* 1667 */         if (this._identifier == 9) {
/* 1668 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.CDATAAlgorithmNotSupported"));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1674 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
/*      */       } 
/* 1676 */       builtInEncodingAlgorithm = BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1681 */     if (builtInEncodingAlgorithm != null) {
/* 1682 */       algorithmData = builtInEncodingAlgorithm.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */     } else {
/*      */       
/* 1685 */       byte[] data = new byte[this._octetBufferLength];
/* 1686 */       System.arraycopy(this._octetBuffer, this._octetBufferStart, data, 0, this._octetBufferLength);
/*      */       
/* 1688 */       algorithmData = data;
/*      */     } 
/*      */     
/* 1691 */     this._attributes.addAttributeWithAlgorithmData(name, URI, this._identifier, algorithmData);
/*      */     
/* 1693 */     if (addToTable) {
/* 1694 */       this._attributeValueTable.add(this._attributes.getValue(this._attributes.getIndex(name.qName)));
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void convertEncodingAlgorithmDataToCharacters() throws FastInfosetException, IOException {
/* 1699 */     StringBuffer buffer = new StringBuffer();
/* 1700 */     if (this._algorithmId == 1)
/* 1701 */     { convertBase64AlorithmDataToCharacters(buffer); }
/* 1702 */     else if (this._algorithmId < 9)
/*      */     
/* 1704 */     { Object array = BuiltInEncodingAlgorithmFactory.getAlgorithm(this._algorithmId).decodeFromBytes(this._algorithmData, this._algorithmDataOffset, this._algorithmDataLength);
/* 1705 */       BuiltInEncodingAlgorithmFactory.getAlgorithm(this._algorithmId).convertToCharacters(array, buffer); }
/* 1706 */     else { if (this._algorithmId == 9) {
/* 1707 */         this._octetBufferOffset -= this._octetBufferLength;
/* 1708 */         decodeUtf8StringIntoCharBuffer();
/*      */         
/* 1710 */         this._characters = this._charBuffer;
/* 1711 */         this._charactersOffset = 0; return;
/*      */       } 
/* 1713 */       if (this._algorithmId >= 32) {
/* 1714 */         EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(this._algorithmURI);
/* 1715 */         if (ea != null) {
/* 1716 */           Object data = ea.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/* 1717 */           ea.convertToCharacters(data, buffer);
/*      */         } else {
/* 1719 */           throw new EncodingAlgorithmException(
/* 1720 */               CommonResourceBundle.getInstance().getString("message.algorithmDataCannotBeReported"));
/*      */         } 
/*      */       }  }
/*      */     
/* 1724 */     this._characters = new char[buffer.length()];
/* 1725 */     buffer.getChars(0, buffer.length(), this._characters, 0);
/* 1726 */     this._charactersOffset = 0;
/* 1727 */     this._charBufferLength = this._characters.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public StAXDocumentParser() {
/* 1732 */     this.base64TaleBytes = new byte[3];
/*      */     reset();
/*      */     this._manager = new StAXManager(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void convertBase64AlorithmDataToCharacters(StringBuffer buffer) throws EncodingAlgorithmException, IOException {
/* 1741 */     int afterTaleOffset = 0;
/*      */     
/* 1743 */     if (this.base64TaleLength > 0) {
/*      */       
/* 1745 */       int bytesToCopy = Math.min(3 - this.base64TaleLength, this._algorithmDataLength);
/* 1746 */       System.arraycopy(this._algorithmData, this._algorithmDataOffset, this.base64TaleBytes, this.base64TaleLength, bytesToCopy);
/* 1747 */       if (this.base64TaleLength + bytesToCopy == 3)
/* 1748 */       { base64DecodeWithCloning(buffer, this.base64TaleBytes, 0, 3); }
/* 1749 */       else { if (!isBase64Follows()) {
/*      */           
/* 1751 */           base64DecodeWithCloning(buffer, this.base64TaleBytes, 0, this.base64TaleLength + bytesToCopy);
/*      */           
/*      */           return;
/*      */         } 
/* 1755 */         this.base64TaleLength += bytesToCopy;
/*      */         
/*      */         return; }
/*      */       
/* 1759 */       afterTaleOffset = bytesToCopy;
/* 1760 */       this.base64TaleLength = 0;
/*      */     } 
/*      */     
/* 1763 */     int taleBytesRemaining = isBase64Follows() ? ((this._algorithmDataLength - afterTaleOffset) % 3) : 0;
/*      */     
/* 1765 */     if (this._isAlgorithmDataCloned) {
/* 1766 */       base64DecodeWithoutCloning(buffer, this._algorithmData, this._algorithmDataOffset + afterTaleOffset, this._algorithmDataLength - afterTaleOffset - taleBytesRemaining);
/*      */     } else {
/*      */       
/* 1769 */       base64DecodeWithCloning(buffer, this._algorithmData, this._algorithmDataOffset + afterTaleOffset, this._algorithmDataLength - afterTaleOffset - taleBytesRemaining);
/*      */     } 
/*      */ 
/*      */     
/* 1773 */     if (taleBytesRemaining > 0) {
/* 1774 */       System.arraycopy(this._algorithmData, this._algorithmDataOffset + this._algorithmDataLength - taleBytesRemaining, this.base64TaleBytes, 0, taleBytesRemaining);
/*      */       
/* 1776 */       this.base64TaleLength = taleBytesRemaining;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void base64DecodeWithCloning(StringBuffer dstBuffer, byte[] data, int offset, int length) throws EncodingAlgorithmException {
/* 1786 */     Object array = BuiltInEncodingAlgorithmFactory.base64EncodingAlgorithm.decodeFromBytes(data, offset, length);
/* 1787 */     BuiltInEncodingAlgorithmFactory.base64EncodingAlgorithm.convertToCharacters(array, dstBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void base64DecodeWithoutCloning(StringBuffer dstBuffer, byte[] data, int offset, int length) throws EncodingAlgorithmException {
/* 1795 */     BuiltInEncodingAlgorithmFactory.base64EncodingAlgorithm.convertToCharacters(data, offset, length, dstBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBase64Follows() throws IOException {
/* 1804 */     int algorithmId, b2, b = peek(this);
/* 1805 */     switch (DecoderStateTables.EII(b)) {
/*      */       case 13:
/* 1807 */         algorithmId = (b & 0x2) << 6;
/* 1808 */         b2 = peek2(this);
/* 1809 */         algorithmId |= (b2 & 0xFC) >> 2;
/*      */         
/* 1811 */         return (algorithmId == 1);
/*      */     } 
/* 1813 */     return false;
/*      */   }
/*      */   
/*      */   protected class NamespaceContextImpl
/*      */     implements NamespaceContext {
/*      */     public final String getNamespaceURI(String prefix) {
/* 1819 */       return StAXDocumentParser.this._prefixTable.getNamespaceFromPrefix(prefix);
/*      */     }
/*      */     
/*      */     public final String getPrefix(String namespaceURI) {
/* 1823 */       return StAXDocumentParser.this._prefixTable.getPrefixFromNamespace(namespaceURI);
/*      */     }
/*      */     
/*      */     public final Iterator getPrefixes(String namespaceURI) {
/* 1827 */       return StAXDocumentParser.this._prefixTable.getPrefixesFromNamespace(namespaceURI);
/*      */     }
/*      */   }
/*      */   
/*      */   public final String getNamespaceDecl(String prefix) {
/* 1832 */     return this._prefixTable.getNamespaceFromPrefix(prefix);
/*      */   }
/*      */   
/*      */   public final String getURI(String prefix) {
/* 1836 */     return getNamespaceDecl(prefix);
/*      */   }
/*      */   
/*      */   public final Iterator getPrefixes() {
/* 1840 */     return this._prefixTable.getPrefixes();
/*      */   }
/*      */   
/*      */   public final AttributesHolder getAttributesHolder() {
/* 1844 */     return this._attributes;
/*      */   }
/*      */   
/*      */   public final void setManager(StAXManager manager) {
/* 1848 */     this._manager = manager;
/*      */   }
/*      */   
/*      */   static final String getEventTypeString(int eventType) {
/* 1852 */     switch (eventType) {
/*      */       case 1:
/* 1854 */         return "START_ELEMENT";
/*      */       case 2:
/* 1856 */         return "END_ELEMENT";
/*      */       case 3:
/* 1858 */         return "PROCESSING_INSTRUCTION";
/*      */       case 4:
/* 1860 */         return "CHARACTERS";
/*      */       case 5:
/* 1862 */         return "COMMENT";
/*      */       case 7:
/* 1864 */         return "START_DOCUMENT";
/*      */       case 8:
/* 1866 */         return "END_DOCUMENT";
/*      */       case 9:
/* 1868 */         return "ENTITY_REFERENCE";
/*      */       case 10:
/* 1870 */         return "ATTRIBUTE";
/*      */       case 11:
/* 1872 */         return "DTD";
/*      */       case 12:
/* 1874 */         return "CDATA";
/*      */     } 
/* 1876 */     return "UNKNOWN_EVENT_TYPE";
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/StAXDocumentParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */