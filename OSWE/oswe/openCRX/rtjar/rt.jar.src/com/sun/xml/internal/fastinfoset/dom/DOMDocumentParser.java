/*      */ package com.sun.xml.internal.fastinfoset.dom;
/*      */ 
/*      */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*      */ import com.sun.xml.internal.fastinfoset.Decoder;
/*      */ import com.sun.xml.internal.fastinfoset.DecoderStateTables;
/*      */ import com.sun.xml.internal.fastinfoset.QualifiedName;
/*      */ import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithmFactory;
/*      */ import com.sun.xml.internal.fastinfoset.util.CharArray;
/*      */ import com.sun.xml.internal.fastinfoset.util.CharArrayString;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithm;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithmException;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.Text;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DOMDocumentParser
/*      */   extends Decoder
/*      */ {
/*      */   protected Document _document;
/*      */   protected Node _currentNode;
/*      */   protected Element _currentElement;
/*   65 */   protected Attr[] _namespaceAttributes = new Attr[16];
/*      */   
/*      */   protected int _namespaceAttributesIndex;
/*      */   
/*   69 */   protected int[] _namespacePrefixes = new int[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _namespacePrefixesIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void parse(Document d, InputStream s) throws FastInfosetException, IOException {
/*   83 */     this._currentNode = this._document = d;
/*   84 */     this._namespaceAttributesIndex = 0;
/*      */     
/*   86 */     parse(s);
/*      */   }
/*      */   
/*      */   protected final void parse(InputStream s) throws FastInfosetException, IOException {
/*   90 */     setInputStream(s);
/*   91 */     parse();
/*      */   }
/*      */   
/*      */   protected void resetOnError() {
/*   95 */     this._namespacePrefixesIndex = 0;
/*      */     
/*   97 */     if (this._v == null) {
/*   98 */       this._prefixTable.clearCompletely();
/*      */     }
/*  100 */     this._duplicateAttributeVerifier.clear();
/*      */   }
/*      */   
/*      */   protected final void parse() throws FastInfosetException, IOException {
/*      */     try {
/*  105 */       reset();
/*  106 */       decodeHeader();
/*  107 */       processDII();
/*  108 */     } catch (RuntimeException e) {
/*  109 */       resetOnError();
/*      */       
/*  111 */       throw new FastInfosetException(e);
/*  112 */     } catch (FastInfosetException e) {
/*  113 */       resetOnError();
/*  114 */       throw e;
/*  115 */     } catch (IOException e) {
/*  116 */       resetOnError();
/*  117 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processDII() throws FastInfosetException, IOException {
/*  122 */     this._b = read();
/*  123 */     if (this._b > 0) {
/*  124 */       processDIIOptionalProperties();
/*      */     }
/*      */ 
/*      */     
/*  128 */     boolean firstElementHasOccured = false;
/*  129 */     boolean documentTypeDeclarationOccured = false;
/*  130 */     while (!this._terminate || !firstElementHasOccured) {
/*  131 */       QualifiedName qn; String system_identifier, public_identifier; this._b = read();
/*  132 */       switch (DecoderStateTables.DII(this._b)) {
/*      */         case 0:
/*  134 */           processEII(this._elementNameTable._array[this._b], false);
/*  135 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 1:
/*  138 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*  139 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 2:
/*  142 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*  143 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 3:
/*  146 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*  147 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 5:
/*  151 */           qn = processLiteralQualifiedName(this._b & 0x3, this._elementNameTable
/*      */               
/*  153 */               .getNext());
/*  154 */           this._elementNameTable.add(qn);
/*  155 */           processEII(qn, ((this._b & 0x40) > 0));
/*  156 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 4:
/*  160 */           processEIIWithNamespaces();
/*  161 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 20:
/*  165 */           if (documentTypeDeclarationOccured) {
/*  166 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.secondOccurenceOfDTDII"));
/*      */           }
/*  168 */           documentTypeDeclarationOccured = true;
/*      */ 
/*      */           
/*  171 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : null;
/*      */           
/*  173 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : null;
/*      */           
/*  175 */           this._b = read();
/*  176 */           while (this._b == 225) {
/*  177 */             switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */               case 0:
/*  179 */                 if (this._addToTable) {
/*  180 */                   this._v.otherString.add(new CharArray(this._charBuffer, 0, this._charBufferLength, true));
/*      */                 }
/*      */                 break;
/*      */               case 2:
/*  184 */                 throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  190 */             this._b = read();
/*      */           } 
/*  192 */           if ((this._b & 0xF0) != 240) {
/*  193 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingInstructionIIsNotTerminatedCorrectly"));
/*      */           }
/*  195 */           if (this._b == 255) {
/*  196 */             this._terminate = true;
/*      */           }
/*      */           
/*  199 */           this._notations.clear();
/*  200 */           this._unparsedEntities.clear();
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 18:
/*  208 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  211 */           processProcessingII();
/*      */           continue;
/*      */         case 23:
/*  214 */           this._doubleTerminate = true;
/*      */         case 22:
/*  216 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  219 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingDII"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  224 */     while (!this._terminate) {
/*  225 */       this._b = read();
/*  226 */       switch (DecoderStateTables.DII(this._b)) {
/*      */         case 18:
/*  228 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  231 */           processProcessingII();
/*      */           continue;
/*      */         case 23:
/*  234 */           this._doubleTerminate = true;
/*      */         case 22:
/*  236 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  239 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingDII"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processDIIOptionalProperties() throws FastInfosetException, IOException {
/*  247 */     if (this._b == 32) {
/*  248 */       decodeInitialVocabulary();
/*      */       
/*      */       return;
/*      */     } 
/*  252 */     if ((this._b & 0x40) > 0) {
/*  253 */       decodeAdditionalData();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  260 */     if ((this._b & 0x20) > 0) {
/*  261 */       decodeInitialVocabulary();
/*      */     }
/*      */     
/*  264 */     if ((this._b & 0x10) > 0) {
/*  265 */       decodeNotations();
/*      */     }
/*      */ 
/*      */     
/*  269 */     if ((this._b & 0x8) > 0) {
/*  270 */       decodeUnparsedEntities();
/*      */     }
/*      */ 
/*      */     
/*  274 */     if ((this._b & 0x4) > 0) {
/*  275 */       decodeCharacterEncodingScheme();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  282 */     if ((this._b & 0x2) > 0) {
/*  283 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  290 */     if ((this._b & 0x1) > 0) {
/*  291 */       decodeVersion();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processEII(QualifiedName name, boolean hasAttributes) throws FastInfosetException, IOException {
/*  300 */     if (this._prefixTable._currentInScope[name.prefixIndex] != name.namespaceNameIndex) {
/*  301 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qnameOfEIINotInScope"));
/*      */     }
/*      */     
/*  304 */     Node parentCurrentNode = this._currentNode;
/*      */     
/*  306 */     this._currentNode = this._currentElement = createElement(name.namespaceName, name.qName, name.localName);
/*      */     
/*  308 */     if (this._namespaceAttributesIndex > 0) {
/*  309 */       for (int i = 0; i < this._namespaceAttributesIndex; i++) {
/*  310 */         this._currentElement.setAttributeNode(this._namespaceAttributes[i]);
/*  311 */         this._namespaceAttributes[i] = null;
/*      */       } 
/*  313 */       this._namespaceAttributesIndex = 0;
/*      */     } 
/*      */     
/*  316 */     if (hasAttributes) {
/*  317 */       processAIIs();
/*      */     }
/*      */     
/*  320 */     parentCurrentNode.appendChild(this._currentElement);
/*      */     
/*  322 */     while (!this._terminate) {
/*  323 */       QualifiedName qn; String v; boolean addToTable; String s; int index; String entity_reference_name, str2, str1, system_identifier, public_identifier; this._b = read();
/*  324 */       switch (DecoderStateTables.EII(this._b)) {
/*      */         case 0:
/*  326 */           processEII(this._elementNameTable._array[this._b], false);
/*      */           continue;
/*      */         case 1:
/*  329 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*      */           continue;
/*      */         case 2:
/*  332 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         case 3:
/*  335 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 5:
/*  339 */           qn = processLiteralQualifiedName(this._b & 0x3, this._elementNameTable
/*      */               
/*  341 */               .getNext());
/*  342 */           this._elementNameTable.add(qn);
/*  343 */           processEII(qn, ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 4:
/*  347 */           processEIIWithNamespaces();
/*      */           continue;
/*      */         
/*      */         case 6:
/*  351 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  353 */           appendOrCreateTextData(processUtf8CharacterString());
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 7:
/*  358 */           this._octetBufferLength = read() + 3;
/*  359 */           appendOrCreateTextData(processUtf8CharacterString());
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 8:
/*  364 */           this
/*      */ 
/*      */             
/*  367 */             ._octetBufferLength = read() << 24 | read() << 16 | read() << 8 | read();
/*  368 */           this._octetBufferLength += 259;
/*  369 */           appendOrCreateTextData(processUtf8CharacterString());
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 9:
/*  374 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  376 */           v = decodeUtf16StringAsString();
/*  377 */           if ((this._b & 0x10) > 0) {
/*  378 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*  381 */           appendOrCreateTextData(v);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 10:
/*  386 */           this._octetBufferLength = read() + 3;
/*  387 */           v = decodeUtf16StringAsString();
/*  388 */           if ((this._b & 0x10) > 0) {
/*  389 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*  392 */           appendOrCreateTextData(v);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 11:
/*  397 */           this
/*      */ 
/*      */             
/*  400 */             ._octetBufferLength = read() << 24 | read() << 16 | read() << 8 | read();
/*  401 */           this._octetBufferLength += 259;
/*  402 */           v = decodeUtf16StringAsString();
/*  403 */           if ((this._b & 0x10) > 0) {
/*  404 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*  407 */           appendOrCreateTextData(v);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 12:
/*  412 */           addToTable = ((this._b & 0x10) > 0);
/*      */ 
/*      */           
/*  415 */           this._identifier = (this._b & 0x2) << 6;
/*  416 */           this._b = read();
/*  417 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  419 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/*  421 */           str2 = decodeRestrictedAlphabetAsString();
/*  422 */           if (addToTable) {
/*  423 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*  426 */           appendOrCreateTextData(str2);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 13:
/*  431 */           addToTable = ((this._b & 0x10) > 0);
/*      */           
/*  433 */           this._identifier = (this._b & 0x2) << 6;
/*  434 */           this._b = read();
/*  435 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  437 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*  438 */           str1 = convertEncodingAlgorithmDataToCharacters(false);
/*  439 */           if (addToTable) {
/*  440 */             this._characterContentChunkTable.add(str1.toCharArray(), str1.length());
/*      */           }
/*  442 */           appendOrCreateTextData(str1);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 14:
/*  447 */           s = this._characterContentChunkTable.getString(this._b & 0xF);
/*      */           
/*  449 */           appendOrCreateTextData(s);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 15:
/*  454 */           index = ((this._b & 0x3) << 8 | read()) + 16;
/*      */           
/*  456 */           str1 = this._characterContentChunkTable.getString(index);
/*      */           
/*  458 */           appendOrCreateTextData(str1);
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 16:
/*  465 */           index = (this._b & 0x3) << 16 | read() << 8 | read();
/*  466 */           index += 1040;
/*  467 */           str1 = this._characterContentChunkTable.getString(index);
/*      */           
/*  469 */           appendOrCreateTextData(str1);
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 17:
/*  476 */           index = read() << 16 | read() << 8 | read();
/*  477 */           index += 263184;
/*  478 */           str1 = this._characterContentChunkTable.getString(index);
/*      */           
/*  480 */           appendOrCreateTextData(str1);
/*      */           continue;
/*      */         
/*      */         case 18:
/*  484 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  487 */           processProcessingII();
/*      */           continue;
/*      */         
/*      */         case 21:
/*  491 */           entity_reference_name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */ 
/*      */           
/*  494 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : null;
/*      */           
/*  496 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : null;
/*      */           continue;
/*      */ 
/*      */ 
/*      */         
/*      */         case 23:
/*  502 */           this._doubleTerminate = true;
/*      */         case 22:
/*  504 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  507 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     } 
/*      */ 
/*      */     
/*  511 */     this._terminate = this._doubleTerminate;
/*  512 */     this._doubleTerminate = false;
/*      */     
/*  514 */     this._currentNode = parentCurrentNode;
/*      */   }
/*      */   
/*      */   private void appendOrCreateTextData(String textData) {
/*  518 */     Node lastChild = this._currentNode.getLastChild();
/*  519 */     if (lastChild instanceof Text) {
/*  520 */       ((Text)lastChild).appendData(textData);
/*      */     } else {
/*  522 */       this._currentNode.appendChild(this._document
/*  523 */           .createTextNode(textData));
/*      */     } 
/*      */   }
/*      */   
/*      */   private final String processUtf8CharacterString() throws FastInfosetException, IOException {
/*  528 */     if ((this._b & 0x10) > 0) {
/*  529 */       this._characterContentChunkTable.ensureSize(this._octetBufferLength);
/*  530 */       int charactersOffset = this._characterContentChunkTable._arrayIndex;
/*  531 */       decodeUtf8StringAsCharBuffer(this._characterContentChunkTable._array, charactersOffset);
/*  532 */       this._characterContentChunkTable.add(this._charBufferLength);
/*  533 */       return this._characterContentChunkTable.getString(this._characterContentChunkTable._cachedIndex);
/*      */     } 
/*  535 */     decodeUtf8StringAsCharBuffer();
/*  536 */     return new String(this._charBuffer, 0, this._charBufferLength);
/*      */   }
/*      */   
/*      */   protected final void processEIIWithNamespaces() throws FastInfosetException, IOException {
/*      */     QualifiedName qn;
/*  541 */     boolean hasAttributes = ((this._b & 0x40) > 0);
/*      */     
/*  543 */     if (++this._prefixTable._declarationId == Integer.MAX_VALUE) {
/*  544 */       this._prefixTable.clearDeclarationIds();
/*      */     }
/*      */ 
/*      */     
/*  548 */     Attr a = null;
/*  549 */     int start = this._namespacePrefixesIndex;
/*  550 */     int b = read();
/*  551 */     while ((b & 0xFC) == 204) {
/*  552 */       String prefix; if (this._namespaceAttributesIndex == this._namespaceAttributes.length) {
/*  553 */         Attr[] newNamespaceAttributes = new Attr[this._namespaceAttributesIndex * 3 / 2 + 1];
/*  554 */         System.arraycopy(this._namespaceAttributes, 0, newNamespaceAttributes, 0, this._namespaceAttributesIndex);
/*  555 */         this._namespaceAttributes = newNamespaceAttributes;
/*      */       } 
/*      */       
/*  558 */       if (this._namespacePrefixesIndex == this._namespacePrefixes.length) {
/*  559 */         int[] namespaceAIIs = new int[this._namespacePrefixesIndex * 3 / 2 + 1];
/*  560 */         System.arraycopy(this._namespacePrefixes, 0, namespaceAIIs, 0, this._namespacePrefixesIndex);
/*  561 */         this._namespacePrefixes = namespaceAIIs;
/*      */       } 
/*      */ 
/*      */       
/*  565 */       switch (b & 0x3) {
/*      */ 
/*      */         
/*      */         case 0:
/*  569 */           a = createAttribute("http://www.w3.org/2000/xmlns/", "xmlns", "xmlns");
/*      */ 
/*      */ 
/*      */           
/*  573 */           a.setValue("");
/*      */           
/*  575 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = -1; this._prefixIndex = this._namespaceNameIndex = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 1:
/*  580 */           a = createAttribute("http://www.w3.org/2000/xmlns/", "xmlns", "xmlns");
/*      */ 
/*      */ 
/*      */           
/*  584 */           a.setValue(decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(false));
/*      */           
/*  586 */           this._prefixIndex = this._namespacePrefixes[this._namespacePrefixesIndex++] = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/*  591 */           prefix = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(false);
/*  592 */           a = createAttribute("http://www.w3.org/2000/xmlns/", 
/*      */               
/*  594 */               createQualifiedNameString(prefix), prefix);
/*      */           
/*  596 */           a.setValue("");
/*      */           
/*  598 */           this._namespaceNameIndex = -1;
/*  599 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = this._prefixIndex;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 3:
/*  604 */           prefix = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(true);
/*  605 */           a = createAttribute("http://www.w3.org/2000/xmlns/", 
/*      */               
/*  607 */               createQualifiedNameString(prefix), prefix);
/*      */           
/*  609 */           a.setValue(decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(true));
/*      */           
/*  611 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = this._prefixIndex;
/*      */           break;
/*      */       } 
/*      */       
/*  615 */       this._prefixTable.pushScope(this._prefixIndex, this._namespaceNameIndex);
/*      */       
/*  617 */       this._namespaceAttributes[this._namespaceAttributesIndex++] = a;
/*      */       
/*  619 */       b = read();
/*      */     } 
/*  621 */     if (b != 240) {
/*  622 */       throw new IOException(CommonResourceBundle.getInstance().getString("message.EIInamespaceNameNotTerminatedCorrectly"));
/*      */     }
/*  624 */     int end = this._namespacePrefixesIndex;
/*      */     
/*  626 */     this._b = read();
/*  627 */     switch (DecoderStateTables.EII(this._b)) {
/*      */       case 0:
/*  629 */         processEII(this._elementNameTable._array[this._b], hasAttributes);
/*      */         break;
/*      */       case 2:
/*  632 */         processEII(decodeEIIIndexMedium(), hasAttributes);
/*      */         break;
/*      */       case 3:
/*  635 */         processEII(decodeEIIIndexLarge(), hasAttributes);
/*      */         break;
/*      */       
/*      */       case 5:
/*  639 */         qn = processLiteralQualifiedName(this._b & 0x3, this._elementNameTable
/*      */             
/*  641 */             .getNext());
/*  642 */         this._elementNameTable.add(qn);
/*  643 */         processEII(qn, hasAttributes);
/*      */         break;
/*      */       
/*      */       default:
/*  647 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEIIAfterAIIs"));
/*      */     } 
/*      */     
/*  650 */     for (int i = start; i < end; i++) {
/*  651 */       this._prefixTable.popScope(this._namespacePrefixes[i]);
/*      */     }
/*  653 */     this._namespacePrefixesIndex = start;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final QualifiedName processLiteralQualifiedName(int state, QualifiedName q) throws FastInfosetException, IOException {
/*  659 */     if (q == null) q = new QualifiedName();
/*      */     
/*  661 */     switch (state) {
/*      */       
/*      */       case 0:
/*  664 */         return q.set(null, null, 
/*      */ 
/*      */             
/*  667 */             decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, -1, this._identifier, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  674 */         return q.set(null, 
/*      */             
/*  676 */             decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(false), 
/*  677 */             decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, this._namespaceNameIndex, this._identifier, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  684 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qNameMissingNamespaceName"));
/*      */       
/*      */       case 3:
/*  687 */         return q.set(
/*  688 */             decodeIdentifyingNonEmptyStringIndexOnFirstBitAsPrefix(true), 
/*  689 */             decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(true), 
/*  690 */             decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), this._prefixIndex, this._namespaceNameIndex, this._identifier, this._charBuffer);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  696 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingEII"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final QualifiedName processLiteralQualifiedName(int state) throws FastInfosetException, IOException {
/*  702 */     switch (state) {
/*      */       
/*      */       case 0:
/*  705 */         return new QualifiedName(null, null, 
/*      */ 
/*      */             
/*  708 */             decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, -1, this._identifier, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  715 */         return new QualifiedName(null, 
/*      */             
/*  717 */             decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(false), 
/*  718 */             decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, this._namespaceNameIndex, this._identifier, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  725 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qNameMissingNamespaceName"));
/*      */       
/*      */       case 3:
/*  728 */         return new QualifiedName(
/*  729 */             decodeIdentifyingNonEmptyStringIndexOnFirstBitAsPrefix(true), 
/*  730 */             decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(true), 
/*  731 */             decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), this._prefixIndex, this._namespaceNameIndex, this._identifier, this._charBuffer);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  737 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingEII"));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */     //   27: invokevirtual read : ()I
/*      */     //   30: istore_2
/*      */     //   31: iload_2
/*      */     //   32: invokestatic AII : (I)I
/*      */     //   35: tableswitch default -> 202, 0 -> 72, 1 -> 85, 2 -> 116, 3 -> 156, 4 -> 194, 5 -> 189
/*      */     //   72: aload_0
/*      */     //   73: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   76: getfield _array : [Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   79: iload_2
/*      */     //   80: aaload
/*      */     //   81: astore_1
/*      */     //   82: goto -> 218
/*      */     //   85: iload_2
/*      */     //   86: bipush #31
/*      */     //   88: iand
/*      */     //   89: bipush #8
/*      */     //   91: ishl
/*      */     //   92: aload_0
/*      */     //   93: invokevirtual read : ()I
/*      */     //   96: ior
/*      */     //   97: bipush #64
/*      */     //   99: iadd
/*      */     //   100: istore #4
/*      */     //   102: aload_0
/*      */     //   103: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   106: getfield _array : [Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   109: iload #4
/*      */     //   111: aaload
/*      */     //   112: astore_1
/*      */     //   113: goto -> 218
/*      */     //   116: iload_2
/*      */     //   117: bipush #15
/*      */     //   119: iand
/*      */     //   120: bipush #16
/*      */     //   122: ishl
/*      */     //   123: aload_0
/*      */     //   124: invokevirtual read : ()I
/*      */     //   127: bipush #8
/*      */     //   129: ishl
/*      */     //   130: ior
/*      */     //   131: aload_0
/*      */     //   132: invokevirtual read : ()I
/*      */     //   135: ior
/*      */     //   136: sipush #8256
/*      */     //   139: iadd
/*      */     //   140: istore #4
/*      */     //   142: aload_0
/*      */     //   143: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   146: getfield _array : [Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   149: iload #4
/*      */     //   151: aaload
/*      */     //   152: astore_1
/*      */     //   153: goto -> 218
/*      */     //   156: aload_0
/*      */     //   157: iload_2
/*      */     //   158: iconst_3
/*      */     //   159: iand
/*      */     //   160: aload_0
/*      */     //   161: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   164: invokevirtual getNext : ()Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   167: invokevirtual processLiteralQualifiedName : (ILcom/sun/xml/internal/fastinfoset/QualifiedName;)Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   170: astore_1
/*      */     //   171: aload_1
/*      */     //   172: sipush #256
/*      */     //   175: invokevirtual createAttributeValues : (I)V
/*      */     //   178: aload_0
/*      */     //   179: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   182: aload_1
/*      */     //   183: invokevirtual add : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;)V
/*      */     //   186: goto -> 218
/*      */     //   189: aload_0
/*      */     //   190: iconst_1
/*      */     //   191: putfield _doubleTerminate : Z
/*      */     //   194: aload_0
/*      */     //   195: iconst_1
/*      */     //   196: putfield _terminate : Z
/*      */     //   199: goto -> 1194
/*      */     //   202: new java/io/IOException
/*      */     //   205: dup
/*      */     //   206: invokestatic getInstance : ()Lcom/sun/xml/internal/fastinfoset/CommonResourceBundle;
/*      */     //   209: ldc 'message.decodingAIIs'
/*      */     //   211: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   214: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   217: athrow
/*      */     //   218: aload_1
/*      */     //   219: getfield prefixIndex : I
/*      */     //   222: ifle -> 260
/*      */     //   225: aload_0
/*      */     //   226: getfield _prefixTable : Lcom/sun/xml/internal/fastinfoset/util/PrefixArray;
/*      */     //   229: getfield _currentInScope : [I
/*      */     //   232: aload_1
/*      */     //   233: getfield prefixIndex : I
/*      */     //   236: iaload
/*      */     //   237: aload_1
/*      */     //   238: getfield namespaceNameIndex : I
/*      */     //   241: if_icmpeq -> 260
/*      */     //   244: new com/sun/xml/internal/org/jvnet/fastinfoset/FastInfosetException
/*      */     //   247: dup
/*      */     //   248: invokestatic getInstance : ()Lcom/sun/xml/internal/fastinfoset/CommonResourceBundle;
/*      */     //   251: ldc 'message.AIIqNameNotInScope'
/*      */     //   253: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   256: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   259: athrow
/*      */     //   260: aload_0
/*      */     //   261: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   264: aload_1
/*      */     //   265: getfield attributeHash : I
/*      */     //   268: aload_1
/*      */     //   269: getfield attributeId : I
/*      */     //   272: invokevirtual checkForDuplicateAttribute : (II)V
/*      */     //   275: aload_0
/*      */     //   276: aload_1
/*      */     //   277: getfield namespaceName : Ljava/lang/String;
/*      */     //   280: aload_1
/*      */     //   281: getfield qName : Ljava/lang/String;
/*      */     //   284: aload_1
/*      */     //   285: getfield localName : Ljava/lang/String;
/*      */     //   288: invokevirtual createAttribute : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/w3c/dom/Attr;
/*      */     //   291: astore #4
/*      */     //   293: aload_0
/*      */     //   294: invokevirtual read : ()I
/*      */     //   297: istore_2
/*      */     //   298: iload_2
/*      */     //   299: invokestatic NISTRING : (I)I
/*      */     //   302: tableswitch default -> 1178, 0 -> 364, 1 -> 430, 2 -> 497, 3 -> 593, 4 -> 659, 5 -> 726, 6 -> 822, 7 -> 914, 8 -> 1007, 9 -> 1043, 10 -> 1094, 11 -> 1154
/*      */     //   364: iload_2
/*      */     //   365: bipush #64
/*      */     //   367: iand
/*      */     //   368: ifle -> 375
/*      */     //   371: iconst_1
/*      */     //   372: goto -> 376
/*      */     //   375: iconst_0
/*      */     //   376: istore #5
/*      */     //   378: aload_0
/*      */     //   379: iload_2
/*      */     //   380: bipush #7
/*      */     //   382: iand
/*      */     //   383: iconst_1
/*      */     //   384: iadd
/*      */     //   385: putfield _octetBufferLength : I
/*      */     //   388: aload_0
/*      */     //   389: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   392: astore_3
/*      */     //   393: iload #5
/*      */     //   395: ifeq -> 407
/*      */     //   398: aload_0
/*      */     //   399: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   402: aload_3
/*      */     //   403: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   406: pop
/*      */     //   407: aload #4
/*      */     //   409: aload_3
/*      */     //   410: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   415: aload_0
/*      */     //   416: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   419: aload #4
/*      */     //   421: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   426: pop
/*      */     //   427: goto -> 1194
/*      */     //   430: iload_2
/*      */     //   431: bipush #64
/*      */     //   433: iand
/*      */     //   434: ifle -> 441
/*      */     //   437: iconst_1
/*      */     //   438: goto -> 442
/*      */     //   441: iconst_0
/*      */     //   442: istore #5
/*      */     //   444: aload_0
/*      */     //   445: aload_0
/*      */     //   446: invokevirtual read : ()I
/*      */     //   449: bipush #9
/*      */     //   451: iadd
/*      */     //   452: putfield _octetBufferLength : I
/*      */     //   455: aload_0
/*      */     //   456: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   459: astore_3
/*      */     //   460: iload #5
/*      */     //   462: ifeq -> 474
/*      */     //   465: aload_0
/*      */     //   466: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   469: aload_3
/*      */     //   470: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   473: pop
/*      */     //   474: aload #4
/*      */     //   476: aload_3
/*      */     //   477: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   482: aload_0
/*      */     //   483: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   486: aload #4
/*      */     //   488: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   493: pop
/*      */     //   494: goto -> 1194
/*      */     //   497: iload_2
/*      */     //   498: bipush #64
/*      */     //   500: iand
/*      */     //   501: ifle -> 508
/*      */     //   504: iconst_1
/*      */     //   505: goto -> 509
/*      */     //   508: iconst_0
/*      */     //   509: istore #5
/*      */     //   511: aload_0
/*      */     //   512: invokevirtual read : ()I
/*      */     //   515: bipush #24
/*      */     //   517: ishl
/*      */     //   518: aload_0
/*      */     //   519: invokevirtual read : ()I
/*      */     //   522: bipush #16
/*      */     //   524: ishl
/*      */     //   525: ior
/*      */     //   526: aload_0
/*      */     //   527: invokevirtual read : ()I
/*      */     //   530: bipush #8
/*      */     //   532: ishl
/*      */     //   533: ior
/*      */     //   534: aload_0
/*      */     //   535: invokevirtual read : ()I
/*      */     //   538: ior
/*      */     //   539: istore #6
/*      */     //   541: aload_0
/*      */     //   542: iload #6
/*      */     //   544: sipush #265
/*      */     //   547: iadd
/*      */     //   548: putfield _octetBufferLength : I
/*      */     //   551: aload_0
/*      */     //   552: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   555: astore_3
/*      */     //   556: iload #5
/*      */     //   558: ifeq -> 570
/*      */     //   561: aload_0
/*      */     //   562: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   565: aload_3
/*      */     //   566: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   569: pop
/*      */     //   570: aload #4
/*      */     //   572: aload_3
/*      */     //   573: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   578: aload_0
/*      */     //   579: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   582: aload #4
/*      */     //   584: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   589: pop
/*      */     //   590: goto -> 1194
/*      */     //   593: iload_2
/*      */     //   594: bipush #64
/*      */     //   596: iand
/*      */     //   597: ifle -> 604
/*      */     //   600: iconst_1
/*      */     //   601: goto -> 605
/*      */     //   604: iconst_0
/*      */     //   605: istore #5
/*      */     //   607: aload_0
/*      */     //   608: iload_2
/*      */     //   609: bipush #7
/*      */     //   611: iand
/*      */     //   612: iconst_1
/*      */     //   613: iadd
/*      */     //   614: putfield _octetBufferLength : I
/*      */     //   617: aload_0
/*      */     //   618: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   621: astore_3
/*      */     //   622: iload #5
/*      */     //   624: ifeq -> 636
/*      */     //   627: aload_0
/*      */     //   628: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   631: aload_3
/*      */     //   632: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   635: pop
/*      */     //   636: aload #4
/*      */     //   638: aload_3
/*      */     //   639: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   644: aload_0
/*      */     //   645: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   648: aload #4
/*      */     //   650: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   655: pop
/*      */     //   656: goto -> 1194
/*      */     //   659: iload_2
/*      */     //   660: bipush #64
/*      */     //   662: iand
/*      */     //   663: ifle -> 670
/*      */     //   666: iconst_1
/*      */     //   667: goto -> 671
/*      */     //   670: iconst_0
/*      */     //   671: istore #5
/*      */     //   673: aload_0
/*      */     //   674: aload_0
/*      */     //   675: invokevirtual read : ()I
/*      */     //   678: bipush #9
/*      */     //   680: iadd
/*      */     //   681: putfield _octetBufferLength : I
/*      */     //   684: aload_0
/*      */     //   685: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   688: astore_3
/*      */     //   689: iload #5
/*      */     //   691: ifeq -> 703
/*      */     //   694: aload_0
/*      */     //   695: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   698: aload_3
/*      */     //   699: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   702: pop
/*      */     //   703: aload #4
/*      */     //   705: aload_3
/*      */     //   706: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   711: aload_0
/*      */     //   712: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   715: aload #4
/*      */     //   717: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   722: pop
/*      */     //   723: goto -> 1194
/*      */     //   726: iload_2
/*      */     //   727: bipush #64
/*      */     //   729: iand
/*      */     //   730: ifle -> 737
/*      */     //   733: iconst_1
/*      */     //   734: goto -> 738
/*      */     //   737: iconst_0
/*      */     //   738: istore #5
/*      */     //   740: aload_0
/*      */     //   741: invokevirtual read : ()I
/*      */     //   744: bipush #24
/*      */     //   746: ishl
/*      */     //   747: aload_0
/*      */     //   748: invokevirtual read : ()I
/*      */     //   751: bipush #16
/*      */     //   753: ishl
/*      */     //   754: ior
/*      */     //   755: aload_0
/*      */     //   756: invokevirtual read : ()I
/*      */     //   759: bipush #8
/*      */     //   761: ishl
/*      */     //   762: ior
/*      */     //   763: aload_0
/*      */     //   764: invokevirtual read : ()I
/*      */     //   767: ior
/*      */     //   768: istore #6
/*      */     //   770: aload_0
/*      */     //   771: iload #6
/*      */     //   773: sipush #265
/*      */     //   776: iadd
/*      */     //   777: putfield _octetBufferLength : I
/*      */     //   780: aload_0
/*      */     //   781: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   784: astore_3
/*      */     //   785: iload #5
/*      */     //   787: ifeq -> 799
/*      */     //   790: aload_0
/*      */     //   791: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   794: aload_3
/*      */     //   795: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   798: pop
/*      */     //   799: aload #4
/*      */     //   801: aload_3
/*      */     //   802: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   807: aload_0
/*      */     //   808: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   811: aload #4
/*      */     //   813: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   818: pop
/*      */     //   819: goto -> 1194
/*      */     //   822: iload_2
/*      */     //   823: bipush #64
/*      */     //   825: iand
/*      */     //   826: ifle -> 833
/*      */     //   829: iconst_1
/*      */     //   830: goto -> 834
/*      */     //   833: iconst_0
/*      */     //   834: istore #5
/*      */     //   836: aload_0
/*      */     //   837: iload_2
/*      */     //   838: bipush #15
/*      */     //   840: iand
/*      */     //   841: iconst_4
/*      */     //   842: ishl
/*      */     //   843: putfield _identifier : I
/*      */     //   846: aload_0
/*      */     //   847: invokevirtual read : ()I
/*      */     //   850: istore_2
/*      */     //   851: aload_0
/*      */     //   852: dup
/*      */     //   853: getfield _identifier : I
/*      */     //   856: iload_2
/*      */     //   857: sipush #240
/*      */     //   860: iand
/*      */     //   861: iconst_4
/*      */     //   862: ishr
/*      */     //   863: ior
/*      */     //   864: putfield _identifier : I
/*      */     //   867: aload_0
/*      */     //   868: iload_2
/*      */     //   869: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   872: aload_0
/*      */     //   873: invokevirtual decodeRestrictedAlphabetAsString : ()Ljava/lang/String;
/*      */     //   876: astore_3
/*      */     //   877: iload #5
/*      */     //   879: ifeq -> 891
/*      */     //   882: aload_0
/*      */     //   883: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   886: aload_3
/*      */     //   887: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   890: pop
/*      */     //   891: aload #4
/*      */     //   893: aload_3
/*      */     //   894: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   899: aload_0
/*      */     //   900: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   903: aload #4
/*      */     //   905: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   910: pop
/*      */     //   911: goto -> 1194
/*      */     //   914: iload_2
/*      */     //   915: bipush #64
/*      */     //   917: iand
/*      */     //   918: ifle -> 925
/*      */     //   921: iconst_1
/*      */     //   922: goto -> 926
/*      */     //   925: iconst_0
/*      */     //   926: istore #5
/*      */     //   928: aload_0
/*      */     //   929: iload_2
/*      */     //   930: bipush #15
/*      */     //   932: iand
/*      */     //   933: iconst_4
/*      */     //   934: ishl
/*      */     //   935: putfield _identifier : I
/*      */     //   938: aload_0
/*      */     //   939: invokevirtual read : ()I
/*      */     //   942: istore_2
/*      */     //   943: aload_0
/*      */     //   944: dup
/*      */     //   945: getfield _identifier : I
/*      */     //   948: iload_2
/*      */     //   949: sipush #240
/*      */     //   952: iand
/*      */     //   953: iconst_4
/*      */     //   954: ishr
/*      */     //   955: ior
/*      */     //   956: putfield _identifier : I
/*      */     //   959: aload_0
/*      */     //   960: iload_2
/*      */     //   961: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   964: aload_0
/*      */     //   965: iconst_1
/*      */     //   966: invokevirtual convertEncodingAlgorithmDataToCharacters : (Z)Ljava/lang/String;
/*      */     //   969: astore_3
/*      */     //   970: iload #5
/*      */     //   972: ifeq -> 984
/*      */     //   975: aload_0
/*      */     //   976: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   979: aload_3
/*      */     //   980: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   983: pop
/*      */     //   984: aload #4
/*      */     //   986: aload_3
/*      */     //   987: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   992: aload_0
/*      */     //   993: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   996: aload #4
/*      */     //   998: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1003: pop
/*      */     //   1004: goto -> 1194
/*      */     //   1007: aload_0
/*      */     //   1008: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   1011: getfield _array : [Ljava/lang/String;
/*      */     //   1014: iload_2
/*      */     //   1015: bipush #63
/*      */     //   1017: iand
/*      */     //   1018: aaload
/*      */     //   1019: astore_3
/*      */     //   1020: aload #4
/*      */     //   1022: aload_3
/*      */     //   1023: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   1028: aload_0
/*      */     //   1029: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   1032: aload #4
/*      */     //   1034: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1039: pop
/*      */     //   1040: goto -> 1194
/*      */     //   1043: iload_2
/*      */     //   1044: bipush #31
/*      */     //   1046: iand
/*      */     //   1047: bipush #8
/*      */     //   1049: ishl
/*      */     //   1050: aload_0
/*      */     //   1051: invokevirtual read : ()I
/*      */     //   1054: ior
/*      */     //   1055: bipush #64
/*      */     //   1057: iadd
/*      */     //   1058: istore #5
/*      */     //   1060: aload_0
/*      */     //   1061: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   1064: getfield _array : [Ljava/lang/String;
/*      */     //   1067: iload #5
/*      */     //   1069: aaload
/*      */     //   1070: astore_3
/*      */     //   1071: aload #4
/*      */     //   1073: aload_3
/*      */     //   1074: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   1079: aload_0
/*      */     //   1080: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   1083: aload #4
/*      */     //   1085: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1090: pop
/*      */     //   1091: goto -> 1194
/*      */     //   1094: iload_2
/*      */     //   1095: bipush #15
/*      */     //   1097: iand
/*      */     //   1098: bipush #16
/*      */     //   1100: ishl
/*      */     //   1101: aload_0
/*      */     //   1102: invokevirtual read : ()I
/*      */     //   1105: bipush #8
/*      */     //   1107: ishl
/*      */     //   1108: ior
/*      */     //   1109: aload_0
/*      */     //   1110: invokevirtual read : ()I
/*      */     //   1113: ior
/*      */     //   1114: sipush #8256
/*      */     //   1117: iadd
/*      */     //   1118: istore #5
/*      */     //   1120: aload_0
/*      */     //   1121: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   1124: getfield _array : [Ljava/lang/String;
/*      */     //   1127: iload #5
/*      */     //   1129: aaload
/*      */     //   1130: astore_3
/*      */     //   1131: aload #4
/*      */     //   1133: aload_3
/*      */     //   1134: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   1139: aload_0
/*      */     //   1140: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   1143: aload #4
/*      */     //   1145: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1150: pop
/*      */     //   1151: goto -> 1194
/*      */     //   1154: aload #4
/*      */     //   1156: ldc ''
/*      */     //   1158: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   1163: aload_0
/*      */     //   1164: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   1167: aload #4
/*      */     //   1169: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1174: pop
/*      */     //   1175: goto -> 1194
/*      */     //   1178: new java/io/IOException
/*      */     //   1181: dup
/*      */     //   1182: invokestatic getInstance : ()Lcom/sun/xml/internal/fastinfoset/CommonResourceBundle;
/*      */     //   1185: ldc 'message.decodingAIIValue'
/*      */     //   1187: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1190: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1193: athrow
/*      */     //   1194: aload_0
/*      */     //   1195: getfield _terminate : Z
/*      */     //   1198: ifeq -> 26
/*      */     //   1201: aload_0
/*      */     //   1202: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   1205: aload_0
/*      */     //   1206: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   1209: getfield _poolHead : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   1212: putfield _poolCurrent : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   1215: aload_0
/*      */     //   1216: aload_0
/*      */     //   1217: getfield _doubleTerminate : Z
/*      */     //   1220: putfield _terminate : Z
/*      */     //   1223: aload_0
/*      */     //   1224: iconst_0
/*      */     //   1225: putfield _doubleTerminate : Z
/*      */     //   1228: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #746	-> 0
/*      */     //   #747	-> 19
/*      */     //   #752	-> 26
/*      */     //   #753	-> 31
/*      */     //   #755	-> 72
/*      */     //   #756	-> 82
/*      */     //   #759	-> 85
/*      */     //   #761	-> 102
/*      */     //   #762	-> 113
/*      */     //   #766	-> 116
/*      */     //   #768	-> 142
/*      */     //   #769	-> 153
/*      */     //   #772	-> 156
/*      */     //   #774	-> 164
/*      */     //   #772	-> 167
/*      */     //   #775	-> 171
/*      */     //   #776	-> 178
/*      */     //   #777	-> 186
/*      */     //   #779	-> 189
/*      */     //   #781	-> 194
/*      */     //   #783	-> 199
/*      */     //   #785	-> 202
/*      */     //   #788	-> 218
/*      */     //   #789	-> 244
/*      */     //   #792	-> 260
/*      */     //   #794	-> 275
/*      */     //   #801	-> 293
/*      */     //   #802	-> 298
/*      */     //   #805	-> 364
/*      */     //   #806	-> 378
/*      */     //   #807	-> 388
/*      */     //   #808	-> 393
/*      */     //   #809	-> 398
/*      */     //   #812	-> 407
/*      */     //   #813	-> 415
/*      */     //   #814	-> 427
/*      */     //   #818	-> 430
/*      */     //   #819	-> 444
/*      */     //   #820	-> 455
/*      */     //   #821	-> 460
/*      */     //   #822	-> 465
/*      */     //   #825	-> 474
/*      */     //   #826	-> 482
/*      */     //   #827	-> 494
/*      */     //   #831	-> 497
/*      */     //   #832	-> 511
/*      */     //   #833	-> 519
/*      */     //   #834	-> 527
/*      */     //   #835	-> 535
/*      */     //   #836	-> 541
/*      */     //   #837	-> 551
/*      */     //   #838	-> 556
/*      */     //   #839	-> 561
/*      */     //   #842	-> 570
/*      */     //   #843	-> 578
/*      */     //   #844	-> 590
/*      */     //   #848	-> 593
/*      */     //   #849	-> 607
/*      */     //   #850	-> 617
/*      */     //   #851	-> 622
/*      */     //   #852	-> 627
/*      */     //   #855	-> 636
/*      */     //   #856	-> 644
/*      */     //   #857	-> 656
/*      */     //   #861	-> 659
/*      */     //   #862	-> 673
/*      */     //   #863	-> 684
/*      */     //   #864	-> 689
/*      */     //   #865	-> 694
/*      */     //   #868	-> 703
/*      */     //   #869	-> 711
/*      */     //   #870	-> 723
/*      */     //   #874	-> 726
/*      */     //   #875	-> 740
/*      */     //   #876	-> 748
/*      */     //   #877	-> 756
/*      */     //   #878	-> 764
/*      */     //   #879	-> 770
/*      */     //   #880	-> 780
/*      */     //   #881	-> 785
/*      */     //   #882	-> 790
/*      */     //   #885	-> 799
/*      */     //   #886	-> 807
/*      */     //   #887	-> 819
/*      */     //   #891	-> 822
/*      */     //   #893	-> 836
/*      */     //   #894	-> 846
/*      */     //   #895	-> 851
/*      */     //   #897	-> 867
/*      */     //   #899	-> 872
/*      */     //   #900	-> 877
/*      */     //   #901	-> 882
/*      */     //   #904	-> 891
/*      */     //   #905	-> 899
/*      */     //   #906	-> 911
/*      */     //   #910	-> 914
/*      */     //   #911	-> 928
/*      */     //   #912	-> 938
/*      */     //   #913	-> 943
/*      */     //   #915	-> 959
/*      */     //   #916	-> 964
/*      */     //   #917	-> 970
/*      */     //   #918	-> 975
/*      */     //   #920	-> 984
/*      */     //   #921	-> 992
/*      */     //   #922	-> 1004
/*      */     //   #925	-> 1007
/*      */     //   #927	-> 1020
/*      */     //   #928	-> 1028
/*      */     //   #929	-> 1040
/*      */     //   #932	-> 1043
/*      */     //   #934	-> 1060
/*      */     //   #936	-> 1071
/*      */     //   #937	-> 1079
/*      */     //   #938	-> 1091
/*      */     //   #942	-> 1094
/*      */     //   #944	-> 1120
/*      */     //   #946	-> 1131
/*      */     //   #947	-> 1139
/*      */     //   #948	-> 1151
/*      */     //   #951	-> 1154
/*      */     //   #952	-> 1163
/*      */     //   #953	-> 1175
/*      */     //   #955	-> 1178
/*      */     //   #958	-> 1194
/*      */     //   #961	-> 1201
/*      */     //   #963	-> 1215
/*      */     //   #964	-> 1223
/*      */     //   #965	-> 1228
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   82	3	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   102	14	4	i	I
/*      */     //   113	3	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   142	14	4	i	I
/*      */     //   153	3	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   171	18	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   378	52	5	addToTable	Z
/*      */     //   393	37	3	value	Ljava/lang/String;
/*      */     //   444	53	5	addToTable	Z
/*      */     //   460	37	3	value	Ljava/lang/String;
/*      */     //   511	82	5	addToTable	Z
/*      */     //   541	52	6	length	I
/*      */     //   556	37	3	value	Ljava/lang/String;
/*      */     //   607	52	5	addToTable	Z
/*      */     //   622	37	3	value	Ljava/lang/String;
/*      */     //   673	53	5	addToTable	Z
/*      */     //   689	37	3	value	Ljava/lang/String;
/*      */     //   740	82	5	addToTable	Z
/*      */     //   770	52	6	length	I
/*      */     //   785	37	3	value	Ljava/lang/String;
/*      */     //   836	78	5	addToTable	Z
/*      */     //   877	37	3	value	Ljava/lang/String;
/*      */     //   928	79	5	addToTable	Z
/*      */     //   970	37	3	value	Ljava/lang/String;
/*      */     //   1020	23	3	value	Ljava/lang/String;
/*      */     //   1060	34	5	index	I
/*      */     //   1071	23	3	value	Ljava/lang/String;
/*      */     //   1120	34	5	index	I
/*      */     //   1131	23	3	value	Ljava/lang/String;
/*      */     //   293	901	4	a	Lorg/w3c/dom/Attr;
/*      */     //   218	976	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   0	1229	0	this	Lcom/sun/xml/internal/fastinfoset/dom/DOMDocumentParser;
/*      */     //   31	1198	2	b	I
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */     String s;
/*  968 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       
/*      */       case 0:
/*  971 */         s = new String(this._charBuffer, 0, this._charBufferLength);
/*  972 */         if (this._addToTable) {
/*  973 */           this._v.otherString.add((CharArray)new CharArrayString(s, false));
/*      */         }
/*      */         
/*  976 */         this._currentNode.appendChild(this._document.createComment(s));
/*      */         break;
/*      */       
/*      */       case 2:
/*  980 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.commentIIAlgorithmNotSupported"));
/*      */       
/*      */       case 1:
/*  983 */         s = this._v.otherString.get(this._integer).toString();
/*      */         
/*  985 */         this._currentNode.appendChild(this._document.createComment(s));
/*      */         break;
/*      */       
/*      */       case 3:
/*  989 */         this._currentNode.appendChild(this._document.createComment(""));
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processProcessingII() throws FastInfosetException, IOException {
/*  995 */     String data, target = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */     
/*  997 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       
/*      */       case 0:
/* 1000 */         data = new String(this._charBuffer, 0, this._charBufferLength);
/* 1001 */         if (this._addToTable) {
/* 1002 */           this._v.otherString.add((CharArray)new CharArrayString(data, false));
/*      */         }
/*      */         
/* 1005 */         this._currentNode.appendChild(this._document.createProcessingInstruction(target, data));
/*      */         break;
/*      */       
/*      */       case 2:
/* 1009 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */       
/*      */       case 1:
/* 1012 */         data = this._v.otherString.get(this._integer).toString();
/*      */         
/* 1014 */         this._currentNode.appendChild(this._document.createProcessingInstruction(target, data));
/*      */         break;
/*      */       
/*      */       case 3:
/* 1018 */         this._currentNode.appendChild(this._document.createProcessingInstruction(target, ""));
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Element createElement(String namespaceName, String qName, String localName) {
/* 1024 */     return this._document.createElementNS(namespaceName, qName);
/*      */   }
/*      */   
/*      */   protected Attr createAttribute(String namespaceName, String qName, String localName) {
/* 1028 */     return this._document.createAttributeNS(namespaceName, qName);
/*      */   }
/*      */   
/*      */   protected String convertEncodingAlgorithmDataToCharacters(boolean isAttributeValue) throws FastInfosetException, IOException {
/* 1032 */     StringBuffer buffer = new StringBuffer();
/* 1033 */     if (this._identifier < 9)
/*      */     
/* 1035 */     { Object array = BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier).decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/* 1036 */       BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier).convertToCharacters(array, buffer); }
/* 1037 */     else { if (this._identifier == 9) {
/* 1038 */         if (!isAttributeValue) {
/*      */           
/* 1040 */           this._octetBufferOffset -= this._octetBufferLength;
/* 1041 */           return decodeUtf8StringAsString();
/*      */         } 
/* 1043 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.CDATAAlgorithmNotSupported"));
/* 1044 */       }  if (this._identifier >= 32) {
/* 1045 */         String URI = this._v.encodingAlgorithm.get(this._identifier - 32);
/* 1046 */         EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 1047 */         if (ea != null) {
/* 1048 */           Object data = ea.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/* 1049 */           ea.convertToCharacters(data, buffer);
/*      */         } else {
/* 1051 */           throw new EncodingAlgorithmException(
/* 1052 */               CommonResourceBundle.getInstance().getString("message.algorithmDataCannotBeReported"));
/*      */         } 
/*      */       }  }
/* 1055 */      return buffer.toString();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/dom/DOMDocumentParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */