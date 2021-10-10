/*      */ package com.sun.org.apache.xml.internal.serialize;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*      */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Writer;
/*      */ import java.util.Map;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.AttributeList;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.helpers.AttributesImpl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLSerializer
/*      */   extends BaseMarkupSerializer
/*      */ {
/*      */   protected static final boolean DEBUG = false;
/*      */   protected NamespaceSupport fNSBinder;
/*      */   protected NamespaceSupport fLocalNSBinder;
/*      */   protected SymbolTable fSymbolTable;
/*      */   protected static final String PREFIX = "NS";
/*      */   protected boolean fNamespaces = false;
/*      */   protected boolean fNamespacePrefixes = true;
/*      */   private boolean fPreserveSpace;
/*      */   
/*      */   public XMLSerializer() {
/*  143 */     super(new OutputFormat("xml", null, false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLSerializer(OutputFormat format) {
/*  153 */     super((format != null) ? format : new OutputFormat("xml", null, false));
/*  154 */     this._format.setMethod("xml");
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
/*      */   public XMLSerializer(Writer writer, OutputFormat format) {
/*  167 */     super((format != null) ? format : new OutputFormat("xml", null, false));
/*  168 */     this._format.setMethod("xml");
/*  169 */     setOutputCharStream(writer);
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
/*      */   public XMLSerializer(OutputStream output, OutputFormat format) {
/*  182 */     super((format != null) ? format : new OutputFormat("xml", null, false));
/*  183 */     this._format.setMethod("xml");
/*  184 */     setOutputByteStream(output);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOutputFormat(OutputFormat format) {
/*  189 */     super.setOutputFormat((format != null) ? format : new OutputFormat("xml", null, false));
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
/*      */   public void setNamespaces(boolean namespaces) {
/*  201 */     this.fNamespaces = namespaces;
/*  202 */     if (this.fNSBinder == null) {
/*  203 */       this.fNSBinder = new NamespaceSupport();
/*  204 */       this.fLocalNSBinder = new NamespaceSupport();
/*  205 */       this.fSymbolTable = new SymbolTable();
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
/*      */   public void startElement(String namespaceURI, String localName, String rawName, Attributes attrs) throws SAXException {
/*  223 */     boolean addNSAttr = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  231 */       if (this._printer == null) {
/*  232 */         String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", null);
/*  233 */         throw new IllegalStateException(msg);
/*      */       } 
/*      */       
/*  236 */       ElementState state = getElementState();
/*  237 */       if (isDocumentState()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  242 */         if (!this._started) {
/*  243 */           startDocument((localName == null || localName.length() == 0) ? rawName : localName);
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  248 */         if (state.empty) {
/*  249 */           this._printer.printText('>');
/*      */         }
/*  251 */         if (state.inCData) {
/*  252 */           this._printer.printText("]]>");
/*  253 */           state.inCData = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  258 */         if (this._indenting && !state.preserveSpace && (state.empty || state.afterElement || state.afterComment))
/*      */         {
/*  260 */           this._printer.breakLine(); } 
/*      */       } 
/*  262 */       boolean preserveSpace = state.preserveSpace;
/*      */ 
/*      */ 
/*      */       
/*  266 */       attrs = extractNamespaces(attrs);
/*      */ 
/*      */ 
/*      */       
/*  270 */       if (rawName == null || rawName.length() == 0) {
/*  271 */         if (localName == null) {
/*  272 */           String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoName", null);
/*  273 */           throw new SAXException(msg);
/*      */         } 
/*  275 */         if (namespaceURI != null && !namespaceURI.equals(""))
/*      */         
/*  277 */         { String prefix = getPrefix(namespaceURI);
/*  278 */           if (prefix != null && prefix.length() > 0) {
/*  279 */             rawName = prefix + ":" + localName;
/*      */           } else {
/*  281 */             rawName = localName;
/*      */           }  }
/*  283 */         else { rawName = localName; }
/*  284 */          addNSAttr = true;
/*      */       } 
/*      */       
/*  287 */       this._printer.printText('<');
/*  288 */       this._printer.printText(rawName);
/*  289 */       this._printer.indent();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  294 */       if (attrs != null) {
/*  295 */         for (int i = 0; i < attrs.getLength(); i++) {
/*  296 */           this._printer.printSpace();
/*      */           
/*  298 */           String str1 = attrs.getQName(i);
/*  299 */           if (str1 != null && str1.length() == 0) {
/*      */ 
/*      */ 
/*      */             
/*  303 */             str1 = attrs.getLocalName(i);
/*  304 */             String attrURI = attrs.getURI(i);
/*  305 */             if (attrURI != null && attrURI.length() != 0 && (namespaceURI == null || namespaceURI
/*  306 */               .length() == 0 || 
/*  307 */               !attrURI.equals(namespaceURI))) {
/*  308 */               String prefix = getPrefix(attrURI);
/*  309 */               if (prefix != null && prefix.length() > 0) {
/*  310 */                 str1 = prefix + ":" + str1;
/*      */               }
/*      */             } 
/*      */           } 
/*  314 */           String value = attrs.getValue(i);
/*  315 */           if (value == null)
/*  316 */             value = ""; 
/*  317 */           this._printer.printText(str1);
/*  318 */           this._printer.printText("=\"");
/*  319 */           printEscaped(value);
/*  320 */           this._printer.printText('"');
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  325 */           if (str1.equals("xml:space")) {
/*  326 */             if (value.equals("preserve")) {
/*  327 */               preserveSpace = true;
/*      */             } else {
/*  329 */               preserveSpace = this._format.getPreserveSpace();
/*      */             } 
/*      */           }
/*      */         } 
/*      */       }
/*  334 */       if (this._prefixes != null) {
/*  335 */         for (Map.Entry<String, String> entry : this._prefixes.entrySet()) {
/*  336 */           this._printer.printSpace();
/*  337 */           String value = entry.getKey();
/*  338 */           String str1 = entry.getValue();
/*  339 */           if (str1.length() == 0) {
/*  340 */             this._printer.printText("xmlns=\"");
/*  341 */             printEscaped(value);
/*  342 */             this._printer.printText('"'); continue;
/*      */           } 
/*  344 */           this._printer.printText("xmlns:");
/*  345 */           this._printer.printText(str1);
/*  346 */           this._printer.printText("=\"");
/*  347 */           printEscaped(value);
/*  348 */           this._printer.printText('"');
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  356 */       state = enterElementState(namespaceURI, localName, rawName, preserveSpace);
/*  357 */       String name = (localName == null || localName.length() == 0) ? rawName : (namespaceURI + "^" + localName);
/*  358 */       state.doCData = this._format.isCDataElement(name);
/*  359 */       state.unescaped = this._format.isNonEscapingElement(name);
/*  360 */     } catch (IOException except) {
/*  361 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement(String namespaceURI, String localName, String rawName) throws SAXException {
/*      */     try {
/*  371 */       endElementIO(namespaceURI, localName, rawName);
/*  372 */     } catch (IOException except) {
/*  373 */       throw new SAXException(except);
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
/*      */   public void endElementIO(String namespaceURI, String localName, String rawName) throws IOException {
/*  389 */     this._printer.unindent();
/*  390 */     ElementState state = getElementState();
/*  391 */     if (state.empty) {
/*  392 */       this._printer.printText("/>");
/*      */     } else {
/*      */       
/*  395 */       if (state.inCData) {
/*  396 */         this._printer.printText("]]>");
/*      */       }
/*      */ 
/*      */       
/*  400 */       if (this._indenting && !state.preserveSpace && (state.afterElement || state.afterComment))
/*  401 */         this._printer.breakLine(); 
/*  402 */       this._printer.printText("</");
/*  403 */       this._printer.printText(state.rawName);
/*  404 */       this._printer.printText('>');
/*      */     } 
/*      */ 
/*      */     
/*  408 */     state = leaveElementState();
/*  409 */     state.afterElement = true;
/*  410 */     state.afterComment = false;
/*  411 */     state.empty = false;
/*  412 */     if (isDocumentState()) {
/*  413 */       this._printer.flush();
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
/*      */   public void startElement(String tagName, AttributeList attrs) throws SAXException {
/*      */     try {
/*  437 */       if (this._printer == null) {
/*  438 */         String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", null);
/*  439 */         throw new IllegalStateException(msg);
/*      */       } 
/*      */       
/*  442 */       ElementState state = getElementState();
/*  443 */       if (isDocumentState()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  448 */         if (!this._started) {
/*  449 */           startDocument(tagName);
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  454 */         if (state.empty) {
/*  455 */           this._printer.printText('>');
/*      */         }
/*  457 */         if (state.inCData) {
/*  458 */           this._printer.printText("]]>");
/*  459 */           state.inCData = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  464 */         if (this._indenting && !state.preserveSpace && (state.empty || state.afterElement || state.afterComment))
/*      */         {
/*  466 */           this._printer.breakLine(); } 
/*      */       } 
/*  468 */       boolean preserveSpace = state.preserveSpace;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  473 */       this._printer.printText('<');
/*  474 */       this._printer.printText(tagName);
/*  475 */       this._printer.indent();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  480 */       if (attrs != null) {
/*  481 */         for (int i = 0; i < attrs.getLength(); i++) {
/*  482 */           this._printer.printSpace();
/*  483 */           String name = attrs.getName(i);
/*  484 */           String value = attrs.getValue(i);
/*  485 */           if (value != null) {
/*  486 */             this._printer.printText(name);
/*  487 */             this._printer.printText("=\"");
/*  488 */             printEscaped(value);
/*  489 */             this._printer.printText('"');
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  495 */           if (name.equals("xml:space")) {
/*  496 */             if (value.equals("preserve")) {
/*  497 */               preserveSpace = true;
/*      */             } else {
/*  499 */               preserveSpace = this._format.getPreserveSpace();
/*      */             } 
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  506 */       state = enterElementState(null, null, tagName, preserveSpace);
/*  507 */       state.doCData = this._format.isCDataElement(tagName);
/*  508 */       state.unescaped = this._format.isNonEscapingElement(tagName);
/*  509 */     } catch (IOException except) {
/*  510 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement(String tagName) throws SAXException {
/*  519 */     endElement((String)null, (String)null, tagName);
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
/*      */   protected void startDocument(String rootTagName) throws IOException {
/*  547 */     String dtd = this._printer.leaveDTD();
/*  548 */     if (!this._started) {
/*      */       
/*  550 */       if (!this._format.getOmitXMLDeclaration()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  555 */         StringBuffer buffer = new StringBuffer("<?xml version=\"");
/*  556 */         if (this._format.getVersion() != null) {
/*  557 */           buffer.append(this._format.getVersion());
/*      */         } else {
/*  559 */           buffer.append("1.0");
/*  560 */         }  buffer.append('"');
/*  561 */         String format_encoding = this._format.getEncoding();
/*  562 */         if (format_encoding != null) {
/*  563 */           buffer.append(" encoding=\"");
/*  564 */           buffer.append(format_encoding);
/*  565 */           buffer.append('"');
/*      */         } 
/*  567 */         if (this._format.getStandalone() && this._docTypeSystemId == null && this._docTypePublicId == null)
/*      */         {
/*  569 */           buffer.append(" standalone=\"yes\""); } 
/*  570 */         buffer.append("?>");
/*  571 */         this._printer.printText(buffer);
/*  572 */         this._printer.breakLine();
/*      */       } 
/*      */       
/*  575 */       if (!this._format.getOmitDocumentType()) {
/*  576 */         if (this._docTypeSystemId != null) {
/*      */ 
/*      */ 
/*      */           
/*  580 */           this._printer.printText("<!DOCTYPE ");
/*  581 */           this._printer.printText(rootTagName);
/*  582 */           if (this._docTypePublicId != null) {
/*  583 */             this._printer.printText(" PUBLIC ");
/*  584 */             printDoctypeURL(this._docTypePublicId);
/*  585 */             if (this._indenting) {
/*  586 */               this._printer.breakLine();
/*  587 */               for (int i = 0; i < 18 + rootTagName.length(); i++)
/*  588 */                 this._printer.printText(" "); 
/*      */             } else {
/*  590 */               this._printer.printText(" ");
/*  591 */             }  printDoctypeURL(this._docTypeSystemId);
/*      */           } else {
/*  593 */             this._printer.printText(" SYSTEM ");
/*  594 */             printDoctypeURL(this._docTypeSystemId);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  599 */           if (dtd != null && dtd.length() > 0) {
/*  600 */             this._printer.printText(" [");
/*  601 */             printText(dtd, true, true);
/*  602 */             this._printer.printText(']');
/*      */           } 
/*      */           
/*  605 */           this._printer.printText(">");
/*  606 */           this._printer.breakLine();
/*  607 */         } else if (dtd != null && dtd.length() > 0) {
/*  608 */           this._printer.printText("<!DOCTYPE ");
/*  609 */           this._printer.printText(rootTagName);
/*  610 */           this._printer.printText(" [");
/*  611 */           printText(dtd, true, true);
/*  612 */           this._printer.printText("]>");
/*  613 */           this._printer.breakLine();
/*      */         } 
/*      */       }
/*      */     } 
/*  617 */     this._started = true;
/*      */     
/*  619 */     serializePreRoot();
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
/*      */   protected void serializeElement(Element elem) throws IOException {
/*  642 */     if (this.fNamespaces) {
/*      */ 
/*      */ 
/*      */       
/*  646 */       this.fLocalNSBinder.reset();
/*      */ 
/*      */       
/*  649 */       this.fNSBinder.pushContext();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  655 */     String tagName = elem.getTagName();
/*  656 */     ElementState state = getElementState();
/*  657 */     if (isDocumentState()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  663 */       if (!this._started) {
/*  664 */         startDocument(tagName);
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  670 */       if (state.empty) {
/*  671 */         this._printer.printText('>');
/*      */       }
/*  673 */       if (state.inCData) {
/*  674 */         this._printer.printText("]]>");
/*  675 */         state.inCData = false;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  680 */       if (this._indenting && !state.preserveSpace && (state.empty || state.afterElement || state.afterComment))
/*      */       {
/*  682 */         this._printer.breakLine();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  687 */     this.fPreserveSpace = state.preserveSpace;
/*      */ 
/*      */     
/*  690 */     int length = 0;
/*  691 */     NamedNodeMap attrMap = null;
/*      */     
/*  693 */     if (elem.hasAttributes()) {
/*  694 */       attrMap = elem.getAttributes();
/*  695 */       length = attrMap.getLength();
/*      */     } 
/*      */     
/*  698 */     if (!this.fNamespaces) {
/*      */ 
/*      */       
/*  701 */       this._printer.printText('<');
/*  702 */       this._printer.printText(tagName);
/*  703 */       this._printer.indent();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  708 */       for (int i = 0; i < length; i++) {
/*  709 */         Attr attr = (Attr)attrMap.item(i);
/*  710 */         String name = attr.getName();
/*  711 */         String value = attr.getValue();
/*  712 */         if (value == null)
/*  713 */           value = ""; 
/*  714 */         printAttribute(name, value, attr.getSpecified(), attr);
/*      */       } 
/*      */     } else {
/*      */       int i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  727 */       for (i = 0; i < length; i++) {
/*      */         
/*  729 */         Attr attr = (Attr)attrMap.item(i);
/*  730 */         String str = attr.getNamespaceURI();
/*      */         
/*  732 */         if (str != null && str.equals(NamespaceContext.XMLNS_URI)) {
/*      */           
/*  734 */           String value = attr.getNodeValue();
/*  735 */           if (value == null) {
/*  736 */             value = XMLSymbols.EMPTY_STRING;
/*      */           }
/*      */           
/*  739 */           if (value.equals(NamespaceContext.XMLNS_URI)) {
/*  740 */             if (this.fDOMErrorHandler != null) {
/*  741 */               String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CantBindXMLNS", null);
/*      */               
/*  743 */               modifyDOMError(msg, (short)2, null, attr);
/*  744 */               boolean continueProcess = this.fDOMErrorHandler.handleError(this.fDOMError);
/*  745 */               if (!continueProcess)
/*      */               {
/*  747 */                 throw new RuntimeException(
/*  748 */                     DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "SerializationStopped", null));
/*      */               }
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  754 */             String str1 = attr.getPrefix();
/*      */             
/*  756 */             str1 = (str1 == null || str1.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(str1);
/*  757 */             String localpart = this.fSymbolTable.addSymbol(attr.getLocalName());
/*  758 */             if (str1 == XMLSymbols.PREFIX_XMLNS) {
/*  759 */               value = this.fSymbolTable.addSymbol(value);
/*      */               
/*  761 */               if (value.length() != 0) {
/*  762 */                 this.fNSBinder.declarePrefix(localpart, value);
/*      */ 
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/*  771 */               value = this.fSymbolTable.addSymbol(value);
/*  772 */               this.fNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, value);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  782 */       String uri = elem.getNamespaceURI();
/*  783 */       String prefix = elem.getPrefix();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  790 */       if (uri != null && prefix != null && uri.length() == 0 && prefix.length() != 0) {
/*      */ 
/*      */ 
/*      */         
/*  794 */         prefix = null;
/*  795 */         this._printer.printText('<');
/*  796 */         this._printer.printText(elem.getLocalName());
/*  797 */         this._printer.indent();
/*      */       } else {
/*  799 */         this._printer.printText('<');
/*  800 */         this._printer.printText(tagName);
/*  801 */         this._printer.indent();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  832 */       if (uri != null) {
/*  833 */         uri = this.fSymbolTable.addSymbol(uri);
/*      */         
/*  835 */         prefix = (prefix == null || prefix.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix);
/*  836 */         if (this.fNSBinder.getURI(prefix) != uri)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  847 */           if (this.fNamespacePrefixes) {
/*  848 */             printNamespaceAttr(prefix, uri);
/*      */           }
/*  850 */           this.fLocalNSBinder.declarePrefix(prefix, uri);
/*  851 */           this.fNSBinder.declarePrefix(prefix, uri);
/*      */         }
/*      */       
/*  854 */       } else if (elem.getLocalName() == null) {
/*      */         
/*  856 */         if (this.fDOMErrorHandler != null) {
/*  857 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalElementName", new Object[] { elem
/*      */                 
/*  859 */                 .getNodeName() });
/*  860 */           modifyDOMError(msg, (short)2, null, elem);
/*  861 */           boolean continueProcess = this.fDOMErrorHandler.handleError(this.fDOMError);
/*      */           
/*  863 */           if (!continueProcess) {
/*  864 */             throw new RuntimeException(
/*  865 */                 DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "SerializationStopped", null));
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  871 */         uri = this.fNSBinder.getURI(XMLSymbols.EMPTY_STRING);
/*      */         
/*  873 */         if (uri != null && uri.length() > 0) {
/*      */ 
/*      */           
/*  876 */           if (this.fNamespacePrefixes) {
/*  877 */             printNamespaceAttr(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
/*      */           }
/*  879 */           this.fLocalNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
/*  880 */           this.fNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  891 */       for (i = 0; i < length; i++) {
/*      */         
/*  893 */         Attr attr = (Attr)attrMap.item(i);
/*  894 */         String value = attr.getValue();
/*  895 */         String name = attr.getNodeName();
/*      */         
/*  897 */         uri = attr.getNamespaceURI();
/*      */ 
/*      */         
/*  900 */         if (uri != null && uri.length() == 0) {
/*  901 */           uri = null;
/*      */           
/*  903 */           name = attr.getLocalName();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  910 */         if (value == null) {
/*  911 */           value = XMLSymbols.EMPTY_STRING;
/*      */         }
/*      */         
/*  914 */         if (uri != null) {
/*  915 */           prefix = attr.getPrefix();
/*  916 */           prefix = (prefix == null) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix);
/*  917 */           String localpart = this.fSymbolTable.addSymbol(attr.getLocalName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  924 */           if (uri != null && uri.equals(NamespaceContext.XMLNS_URI)) {
/*      */             
/*  926 */             prefix = attr.getPrefix();
/*      */             
/*  928 */             prefix = (prefix == null || prefix.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix);
/*  929 */             localpart = this.fSymbolTable.addSymbol(attr.getLocalName());
/*  930 */             if (prefix == XMLSymbols.PREFIX_XMLNS) {
/*  931 */               String localUri = this.fLocalNSBinder.getURI(localpart);
/*  932 */               value = this.fSymbolTable.addSymbol(value);
/*  933 */               if (value.length() != 0 && 
/*  934 */                 localUri == null)
/*      */               {
/*      */ 
/*      */ 
/*      */                 
/*  939 */                 if (this.fNamespacePrefixes) {
/*  940 */                   printNamespaceAttr(localpart, value);
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  949 */                 this.fLocalNSBinder.declarePrefix(localpart, value);
/*      */ 
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/*  959 */               uri = this.fNSBinder.getURI(XMLSymbols.EMPTY_STRING);
/*  960 */               String localUri = this.fLocalNSBinder.getURI(XMLSymbols.EMPTY_STRING);
/*  961 */               value = this.fSymbolTable.addSymbol(value);
/*  962 */               if (localUri == null)
/*      */               {
/*  964 */                 if (this.fNamespacePrefixes) {
/*  965 */                   printNamespaceAttr(XMLSymbols.EMPTY_STRING, value);
/*      */                 
/*      */                 }
/*      */               }
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  974 */             uri = this.fSymbolTable.addSymbol(uri);
/*      */ 
/*      */             
/*  977 */             String declaredURI = this.fNSBinder.getURI(prefix);
/*      */             
/*  979 */             if (prefix == XMLSymbols.EMPTY_STRING || declaredURI != uri) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  986 */               name = attr.getNodeName();
/*      */ 
/*      */               
/*  989 */               String declaredPrefix = this.fNSBinder.getPrefix(uri);
/*      */               
/*  991 */               if (declaredPrefix != null && declaredPrefix != XMLSymbols.EMPTY_STRING) {
/*      */                 
/*  993 */                 prefix = declaredPrefix;
/*  994 */                 name = prefix + ":" + localpart;
/*      */               
/*      */               }
/*      */               else {
/*      */ 
/*      */                 
/* 1000 */                 if (prefix == XMLSymbols.EMPTY_STRING || this.fLocalNSBinder.getURI(prefix) != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1007 */                   int counter = 1;
/* 1008 */                   prefix = this.fSymbolTable.addSymbol("NS" + counter++);
/* 1009 */                   while (this.fLocalNSBinder.getURI(prefix) != null) {
/* 1010 */                     prefix = this.fSymbolTable.addSymbol("NS" + counter++);
/*      */                   }
/* 1012 */                   name = prefix + ":" + localpart;
/*      */                 } 
/*      */                 
/* 1015 */                 if (this.fNamespacePrefixes) {
/* 1016 */                   printNamespaceAttr(prefix, uri);
/*      */                 }
/* 1018 */                 value = this.fSymbolTable.addSymbol(value);
/* 1019 */                 this.fLocalNSBinder.declarePrefix(prefix, value);
/* 1020 */                 this.fNSBinder.declarePrefix(prefix, uri);
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1026 */             printAttribute(name, (value == null) ? XMLSymbols.EMPTY_STRING : value, attr.getSpecified(), attr);
/*      */           } 
/* 1028 */         } else if (attr.getLocalName() == null) {
/* 1029 */           if (this.fDOMErrorHandler != null) {
/* 1030 */             String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalAttrName", new Object[] { attr
/*      */                   
/* 1032 */                   .getNodeName() });
/* 1033 */             modifyDOMError(msg, (short)2, null, attr);
/* 1034 */             boolean continueProcess = this.fDOMErrorHandler.handleError(this.fDOMError);
/* 1035 */             if (!continueProcess)
/*      */             {
/* 1037 */               throw new RuntimeException(
/* 1038 */                   DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "SerializationStopped", null));
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 1043 */           printAttribute(name, value, attr.getSpecified(), attr);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1048 */           printAttribute(name, value, attr.getSpecified(), attr);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1058 */     if (elem.hasChildNodes()) {
/*      */ 
/*      */       
/* 1061 */       state = enterElementState(null, null, tagName, this.fPreserveSpace);
/* 1062 */       state.doCData = this._format.isCDataElement(tagName);
/* 1063 */       state.unescaped = this._format.isNonEscapingElement(tagName);
/* 1064 */       Node child = elem.getFirstChild();
/* 1065 */       while (child != null) {
/* 1066 */         serializeNode(child);
/* 1067 */         child = child.getNextSibling();
/*      */       } 
/* 1069 */       if (this.fNamespaces) {
/* 1070 */         this.fNSBinder.popContext();
/*      */       }
/* 1072 */       endElementIO((String)null, (String)null, tagName);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1077 */       if (this.fNamespaces) {
/* 1078 */         this.fNSBinder.popContext();
/*      */       }
/* 1080 */       this._printer.unindent();
/* 1081 */       this._printer.printText("/>");
/*      */       
/* 1083 */       state.afterElement = true;
/* 1084 */       state.afterComment = false;
/* 1085 */       state.empty = false;
/* 1086 */       if (isDocumentState()) {
/* 1087 */         this._printer.flush();
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
/*      */   private void printNamespaceAttr(String prefix, String uri) throws IOException {
/* 1103 */     this._printer.printSpace();
/* 1104 */     if (prefix == XMLSymbols.EMPTY_STRING) {
/*      */ 
/*      */ 
/*      */       
/* 1108 */       this._printer.printText(XMLSymbols.PREFIX_XMLNS);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1113 */       this._printer.printText("xmlns:" + prefix);
/*      */     } 
/* 1115 */     this._printer.printText("=\"");
/* 1116 */     printEscaped(uri);
/* 1117 */     this._printer.printText('"');
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
/*      */   private void printAttribute(String name, String value, boolean isSpecified, Attr attr) throws IOException {
/* 1133 */     if (isSpecified || (this.features & 0x40) == 0) {
/* 1134 */       if (this.fDOMFilter != null && (this.fDOMFilter
/* 1135 */         .getWhatToShow() & 0x2) != 0) {
/* 1136 */         short code = this.fDOMFilter.acceptNode(attr);
/* 1137 */         switch (code) {
/*      */           case 2:
/*      */           case 3:
/*      */             return;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       } 
/* 1147 */       this._printer.printSpace();
/* 1148 */       this._printer.printText(name);
/* 1149 */       this._printer.printText("=\"");
/* 1150 */       printEscaped(value);
/* 1151 */       this._printer.printText('"');
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1157 */     if (name.equals("xml:space")) {
/* 1158 */       if (value.equals("preserve")) {
/* 1159 */         this.fPreserveSpace = true;
/*      */       } else {
/* 1161 */         this.fPreserveSpace = this._format.getPreserveSpace();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getEntityRef(int ch) {
/* 1168 */     switch (ch) {
/*      */       case 60:
/* 1170 */         return "lt";
/*      */       case 62:
/* 1172 */         return "gt";
/*      */       case 34:
/* 1174 */         return "quot";
/*      */       case 39:
/* 1176 */         return "apos";
/*      */       case 38:
/* 1178 */         return "amp";
/*      */     } 
/* 1180 */     return null;
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
/*      */   private Attributes extractNamespaces(Attributes attrs) throws SAXException {
/* 1197 */     if (attrs == null) {
/* 1198 */       return null;
/*      */     }
/* 1200 */     int length = attrs.getLength();
/* 1201 */     AttributesImpl attrsOnly = new AttributesImpl(attrs);
/*      */     
/* 1203 */     for (int i = length - 1; i >= 0; i--) {
/* 1204 */       String rawName = attrsOnly.getQName(i);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1209 */       if (rawName.startsWith("xmlns")) {
/* 1210 */         if (rawName.length() == 5) {
/* 1211 */           startPrefixMapping("", attrs.getValue(i));
/* 1212 */           attrsOnly.removeAttribute(i);
/* 1213 */         } else if (rawName.charAt(5) == ':') {
/* 1214 */           startPrefixMapping(rawName.substring(6), attrs.getValue(i));
/* 1215 */           attrsOnly.removeAttribute(i);
/*      */         } 
/*      */       }
/*      */     } 
/* 1219 */     return attrsOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void printEscaped(String source) throws IOException {
/* 1226 */     int length = source.length();
/* 1227 */     for (int i = 0; i < length; i++) {
/* 1228 */       int ch = source.charAt(i);
/* 1229 */       if (!XMLChar.isValid(ch)) {
/* 1230 */         if (++i < length) {
/* 1231 */           surrogates(ch, source.charAt(i));
/*      */         } else {
/* 1233 */           fatalError("The character '" + (char)ch + "' is an invalid XML character");
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1238 */       else if (ch == 10 || ch == 13 || ch == 9) {
/* 1239 */         printHex(ch);
/* 1240 */       } else if (ch == 60) {
/* 1241 */         this._printer.printText("&lt;");
/* 1242 */       } else if (ch == 38) {
/* 1243 */         this._printer.printText("&amp;");
/* 1244 */       } else if (ch == 34) {
/* 1245 */         this._printer.printText("&quot;");
/* 1246 */       } else if (ch >= 32 && this._encodingInfo.isPrintable((char)ch)) {
/* 1247 */         this._printer.printText((char)ch);
/*      */       } else {
/* 1249 */         printHex(ch);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void printXMLChar(int ch) throws IOException {
/* 1256 */     if (ch == 13) {
/* 1257 */       printHex(ch);
/* 1258 */     } else if (ch == 60) {
/* 1259 */       this._printer.printText("&lt;");
/* 1260 */     } else if (ch == 38) {
/* 1261 */       this._printer.printText("&amp;");
/* 1262 */     } else if (ch == 62) {
/*      */ 
/*      */       
/* 1265 */       this._printer.printText("&gt;");
/* 1266 */     } else if (ch == 10 || ch == 9 || (ch >= 32 && this._encodingInfo
/* 1267 */       .isPrintable((char)ch))) {
/* 1268 */       this._printer.printText((char)ch);
/*      */     } else {
/* 1270 */       printHex(ch);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void printText(String text, boolean preserveSpace, boolean unescaped) throws IOException {
/* 1278 */     int length = text.length();
/* 1279 */     if (preserveSpace) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1284 */       for (int index = 0; index < length; index++) {
/* 1285 */         char ch = text.charAt(index);
/* 1286 */         if (!XMLChar.isValid(ch)) {
/*      */           
/* 1288 */           if (++index < length) {
/* 1289 */             surrogates(ch, text.charAt(index));
/*      */           } else {
/* 1291 */             fatalError("The character '" + ch + "' is an invalid XML character");
/*      */           }
/*      */         
/*      */         }
/* 1295 */         else if (unescaped) {
/* 1296 */           this._printer.printText(ch);
/*      */         } else {
/* 1298 */           printXMLChar(ch);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1306 */       for (int index = 0; index < length; index++) {
/* 1307 */         char ch = text.charAt(index);
/* 1308 */         if (!XMLChar.isValid(ch)) {
/*      */           
/* 1310 */           if (++index < length) {
/* 1311 */             surrogates(ch, text.charAt(index));
/*      */           } else {
/* 1313 */             fatalError("The character '" + ch + "' is an invalid XML character");
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 1318 */         else if (unescaped) {
/* 1319 */           this._printer.printText(ch);
/*      */         } else {
/* 1321 */           printXMLChar(ch);
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
/*      */   protected void printText(char[] chars, int start, int length, boolean preserveSpace, boolean unescaped) throws IOException {
/* 1333 */     if (preserveSpace) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1338 */       while (length-- > 0) {
/* 1339 */         char ch = chars[start++];
/* 1340 */         if (!XMLChar.isValid(ch)) {
/*      */           
/* 1342 */           if (length-- > 0) {
/* 1343 */             surrogates(ch, chars[start++]); continue;
/*      */           } 
/* 1345 */           fatalError("The character '" + ch + "' is an invalid XML character");
/*      */           
/*      */           continue;
/*      */         } 
/* 1349 */         if (unescaped) {
/* 1350 */           this._printer.printText(ch); continue;
/*      */         } 
/* 1352 */         printXMLChar(ch);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1360 */       while (length-- > 0) {
/* 1361 */         char ch = chars[start++];
/* 1362 */         if (!XMLChar.isValid(ch)) {
/*      */           
/* 1364 */           if (length-- > 0) {
/* 1365 */             surrogates(ch, chars[start++]); continue;
/*      */           } 
/* 1367 */           fatalError("The character '" + ch + "' is an invalid XML character");
/*      */           
/*      */           continue;
/*      */         } 
/* 1371 */         if (unescaped) {
/* 1372 */           this._printer.printText(ch); continue;
/*      */         } 
/* 1374 */         printXMLChar(ch);
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
/*      */   protected void checkUnboundNamespacePrefixedNode(Node node) throws IOException {
/* 1388 */     if (this.fNamespaces)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1401 */       for (Node child = node.getFirstChild(); child != null; child = next) {
/* 1402 */         Node next = child.getNextSibling();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1410 */         String prefix = child.getPrefix();
/*      */         
/* 1412 */         prefix = (prefix == null || prefix.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix);
/* 1413 */         if (this.fNSBinder.getURI(prefix) == null && prefix != null) {
/* 1414 */           fatalError("The replacement text of the entity node '" + node
/* 1415 */               .getNodeName() + "' contains an element node '" + child
/*      */               
/* 1417 */               .getNodeName() + "' with an undeclared prefix '" + prefix + "'.");
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1422 */         if (child.getNodeType() == 1) {
/*      */           
/* 1424 */           NamedNodeMap attrs = child.getAttributes();
/*      */           
/* 1426 */           for (int i = 0; i < attrs.getLength(); i++) {
/*      */             
/* 1428 */             String attrPrefix = attrs.item(i).getPrefix();
/*      */             
/* 1430 */             attrPrefix = (attrPrefix == null || attrPrefix.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(attrPrefix);
/* 1431 */             if (this.fNSBinder.getURI(attrPrefix) == null && attrPrefix != null) {
/* 1432 */               fatalError("The replacement text of the entity node '" + node
/* 1433 */                   .getNodeName() + "' contains an element node '" + child
/*      */                   
/* 1435 */                   .getNodeName() + "' with an attribute '" + attrs
/*      */                   
/* 1437 */                   .item(i).getNodeName() + "' an undeclared prefix '" + attrPrefix + "'.");
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1446 */         if (child.hasChildNodes()) {
/* 1447 */           checkUnboundNamespacePrefixedNode(child);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean reset() {
/* 1454 */     super.reset();
/* 1455 */     if (this.fNSBinder != null) {
/* 1456 */       this.fNSBinder.reset();
/*      */ 
/*      */       
/* 1459 */       this.fNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
/*      */     } 
/* 1461 */     return true;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/XMLSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */