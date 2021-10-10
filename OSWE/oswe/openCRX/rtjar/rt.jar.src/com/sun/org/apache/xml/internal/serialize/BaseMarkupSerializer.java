/*      */ package com.sun.org.apache.xml.internal.serialize;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMErrorImpl;
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMLocatorImpl;
/*      */ import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import org.w3c.dom.DOMError;
/*      */ import org.w3c.dom.DOMErrorHandler;
/*      */ import org.w3c.dom.DOMImplementation;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.DocumentFragment;
/*      */ import org.w3c.dom.DocumentType;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.ls.LSException;
/*      */ import org.w3c.dom.ls.LSSerializerFilter;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.DocumentHandler;
/*      */ import org.xml.sax.Locator;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.ext.DeclHandler;
/*      */ import org.xml.sax.ext.LexicalHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class BaseMarkupSerializer
/*      */   implements ContentHandler, DocumentHandler, LexicalHandler, DTDHandler, DeclHandler, DOMSerializer, Serializer
/*      */ {
/*  136 */   protected short features = -1;
/*      */   protected DOMErrorHandler fDOMErrorHandler;
/*  138 */   protected final DOMErrorImpl fDOMError = new DOMErrorImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LSSerializerFilter fDOMFilter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EncodingInfo _encodingInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ElementState[] _elementStates;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int _elementStateCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector _preRoot;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _started;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _prepared;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map<String, String> _prefixes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _docTypePublicId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _docTypeSystemId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected OutputFormat _format;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Printer _printer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _indenting;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  224 */   protected final StringBuffer fStrBuffer = new StringBuffer(40);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Writer _writer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private OutputStream _output;
/*      */ 
/*      */ 
/*      */   
/*  238 */   protected Node fCurrentNode = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BaseMarkupSerializer(OutputFormat format) {
/*  257 */     this._elementStates = new ElementState[10];
/*  258 */     for (int i = 0; i < this._elementStates.length; i++)
/*  259 */       this._elementStates[i] = new ElementState(); 
/*  260 */     this._format = format;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DocumentHandler asDocumentHandler() throws IOException {
/*  267 */     prepare();
/*  268 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ContentHandler asContentHandler() throws IOException {
/*  275 */     prepare();
/*  276 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DOMSerializer asDOMSerializer() throws IOException {
/*  283 */     prepare();
/*  284 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutputByteStream(OutputStream output) {
/*  290 */     if (output == null) {
/*  291 */       String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ArgumentIsNull", new Object[] { "output" });
/*      */       
/*  293 */       throw new NullPointerException(msg);
/*      */     } 
/*  295 */     this._output = output;
/*  296 */     this._writer = null;
/*  297 */     reset();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutputCharStream(Writer writer) {
/*  303 */     if (writer == null) {
/*  304 */       String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ArgumentIsNull", new Object[] { "writer" });
/*      */       
/*  306 */       throw new NullPointerException(msg);
/*      */     } 
/*  308 */     this._writer = writer;
/*  309 */     this._output = null;
/*  310 */     reset();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutputFormat(OutputFormat format) {
/*  316 */     if (format == null) {
/*  317 */       String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ArgumentIsNull", new Object[] { "format" });
/*      */       
/*  319 */       throw new NullPointerException(msg);
/*      */     } 
/*  321 */     this._format = format;
/*  322 */     reset();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean reset() {
/*  328 */     if (this._elementStateCount > 1) {
/*  329 */       String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ResetInMiddle", null);
/*      */       
/*  331 */       throw new IllegalStateException(msg);
/*      */     } 
/*  333 */     this._prepared = false;
/*  334 */     this.fCurrentNode = null;
/*  335 */     this.fStrBuffer.setLength(0);
/*  336 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void prepare() throws IOException {
/*  343 */     if (this._prepared) {
/*      */       return;
/*      */     }
/*  346 */     if (this._writer == null && this._output == null) {
/*  347 */       String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", null);
/*      */       
/*  349 */       throw new IOException(msg);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  355 */     this._encodingInfo = this._format.getEncodingInfo();
/*      */     
/*  357 */     if (this._output != null) {
/*  358 */       this._writer = this._encodingInfo.getWriter(this._output);
/*      */     }
/*      */     
/*  361 */     if (this._format.getIndenting()) {
/*  362 */       this._indenting = true;
/*  363 */       this._printer = new IndentPrinter(this._writer, this._format);
/*      */     } else {
/*  365 */       this._indenting = false;
/*  366 */       this._printer = new Printer(this._writer, this._format);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  371 */     this._elementStateCount = 0;
/*  372 */     ElementState state = this._elementStates[0];
/*  373 */     state.namespaceURI = null;
/*  374 */     state.localName = null;
/*  375 */     state.rawName = null;
/*  376 */     state.preserveSpace = this._format.getPreserveSpace();
/*  377 */     state.empty = true;
/*  378 */     state.afterElement = false;
/*  379 */     state.afterComment = false;
/*  380 */     state.doCData = state.inCData = false;
/*  381 */     state.prefixes = null;
/*      */     
/*  383 */     this._docTypePublicId = this._format.getDoctypePublic();
/*  384 */     this._docTypeSystemId = this._format.getDoctypeSystem();
/*  385 */     this._started = false;
/*  386 */     this._prepared = true;
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
/*      */   public void serialize(Element elem) throws IOException {
/*  408 */     reset();
/*  409 */     prepare();
/*  410 */     serializeNode(elem);
/*  411 */     this._printer.flush();
/*  412 */     if (this._printer.getException() != null) {
/*  413 */       throw this._printer.getException();
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
/*      */   public void serialize(Node node) throws IOException {
/*  425 */     reset();
/*  426 */     prepare();
/*  427 */     serializeNode(node);
/*      */     
/*  429 */     serializePreRoot();
/*  430 */     this._printer.flush();
/*  431 */     if (this._printer.getException() != null) {
/*  432 */       throw this._printer.getException();
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
/*      */   public void serialize(DocumentFragment frag) throws IOException {
/*  447 */     reset();
/*  448 */     prepare();
/*  449 */     serializeNode(frag);
/*  450 */     this._printer.flush();
/*  451 */     if (this._printer.getException() != null) {
/*  452 */       throw this._printer.getException();
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
/*      */   public void serialize(Document doc) throws IOException {
/*  468 */     reset();
/*  469 */     prepare();
/*  470 */     serializeNode(doc);
/*  471 */     serializePreRoot();
/*  472 */     this._printer.flush();
/*  473 */     if (this._printer.getException() != null) {
/*  474 */       throw this._printer.getException();
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
/*      */   public void startDocument() throws SAXException {
/*      */     try {
/*  487 */       prepare();
/*  488 */     } catch (IOException except) {
/*  489 */       throw new SAXException(except.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void characters(char[] chars, int start, int length) throws SAXException {
/*      */     try {
/*  501 */       ElementState state = content();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  507 */       if (state.inCData || state.doCData) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  513 */         if (!state.inCData) {
/*  514 */           this._printer.printText("<![CDATA[");
/*  515 */           state.inCData = true;
/*      */         } 
/*  517 */         int saveIndent = this._printer.getNextIndent();
/*  518 */         this._printer.setNextIndent(0);
/*      */         
/*  520 */         int end = start + length;
/*  521 */         for (int index = start; index < end; index++) {
/*  522 */           char ch = chars[index];
/*  523 */           if (ch == ']' && index + 2 < end && chars[index + 1] == ']' && chars[index + 2] == '>') {
/*      */             
/*  525 */             this._printer.printText("]]]]><![CDATA[>");
/*  526 */             index += 2;
/*      */           
/*      */           }
/*  529 */           else if (!XMLChar.isValid(ch)) {
/*      */             
/*  531 */             if (++index < end) {
/*  532 */               surrogates(ch, chars[index]);
/*      */             } else {
/*      */               
/*  535 */               fatalError("The character '" + ch + "' is an invalid XML character");
/*      */             }
/*      */           
/*      */           }
/*  539 */           else if ((ch >= ' ' && this._encodingInfo.isPrintable(ch) && ch != '÷') || ch == '\n' || ch == '\r' || ch == '\t') {
/*      */             
/*  541 */             this._printer.printText(ch);
/*      */           } else {
/*      */             
/*  544 */             this._printer.printText("]]>&#x");
/*  545 */             this._printer.printText(Integer.toHexString(ch));
/*  546 */             this._printer.printText(";<![CDATA[");
/*      */           } 
/*      */         } 
/*      */         
/*  550 */         this._printer.setNextIndent(saveIndent);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  556 */       else if (state.preserveSpace) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  561 */         int saveIndent = this._printer.getNextIndent();
/*  562 */         this._printer.setNextIndent(0);
/*  563 */         printText(chars, start, length, true, state.unescaped);
/*  564 */         this._printer.setNextIndent(saveIndent);
/*      */       } else {
/*  566 */         printText(chars, start, length, false, state.unescaped);
/*      */       }
/*      */     
/*  569 */     } catch (IOException except) {
/*  570 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ignorableWhitespace(char[] chars, int start, int length) throws SAXException {
/*      */     try {
/*  581 */       content();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  586 */       if (this._indenting) {
/*  587 */         this._printer.setThisIndent(0);
/*  588 */         for (int i = start; length-- > 0; i++)
/*  589 */           this._printer.printText(chars[i]); 
/*      */       } 
/*  591 */     } catch (IOException except) {
/*  592 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void processingInstruction(String target, String code) throws SAXException {
/*      */     try {
/*  601 */       processingInstructionIO(target, code);
/*  602 */     } catch (IOException except) {
/*  603 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processingInstructionIO(String target, String code) throws IOException {
/*  613 */     ElementState state = content();
/*      */ 
/*      */ 
/*      */     
/*  617 */     int index = target.indexOf("?>");
/*  618 */     if (index >= 0) {
/*  619 */       this.fStrBuffer.append("<?").append(target.substring(0, index));
/*      */     } else {
/*  621 */       this.fStrBuffer.append("<?").append(target);
/*  622 */     }  if (code != null) {
/*  623 */       this.fStrBuffer.append(' ');
/*  624 */       index = code.indexOf("?>");
/*  625 */       if (index >= 0) {
/*  626 */         this.fStrBuffer.append(code.substring(0, index));
/*      */       } else {
/*  628 */         this.fStrBuffer.append(code);
/*      */       } 
/*  630 */     }  this.fStrBuffer.append("?>");
/*      */ 
/*      */ 
/*      */     
/*  634 */     if (isDocumentState()) {
/*  635 */       if (this._preRoot == null)
/*  636 */         this._preRoot = new Vector(); 
/*  637 */       this._preRoot.addElement(this.fStrBuffer.toString());
/*      */     } else {
/*  639 */       this._printer.indent();
/*  640 */       printText(this.fStrBuffer.toString(), true, true);
/*  641 */       this._printer.unindent();
/*  642 */       if (this._indenting) {
/*  643 */         state.afterElement = true;
/*      */       }
/*      */     } 
/*  646 */     this.fStrBuffer.setLength(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void comment(char[] chars, int start, int length) throws SAXException {
/*      */     try {
/*  654 */       comment(new String(chars, start, length));
/*  655 */     } catch (IOException except) {
/*  656 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void comment(String text) throws IOException {
/*  667 */     if (this._format.getOmitComments()) {
/*      */       return;
/*      */     }
/*  670 */     ElementState state = content();
/*      */ 
/*      */     
/*  673 */     int index = text.indexOf("-->");
/*  674 */     if (index >= 0) {
/*  675 */       this.fStrBuffer.append("<!--").append(text.substring(0, index)).append("-->");
/*      */     } else {
/*  677 */       this.fStrBuffer.append("<!--").append(text).append("-->");
/*      */     } 
/*      */ 
/*      */     
/*  681 */     if (isDocumentState()) {
/*  682 */       if (this._preRoot == null)
/*  683 */         this._preRoot = new Vector(); 
/*  684 */       this._preRoot.addElement(this.fStrBuffer.toString());
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  689 */       if (this._indenting && !state.preserveSpace)
/*  690 */         this._printer.breakLine(); 
/*  691 */       this._printer.indent();
/*  692 */       printText(this.fStrBuffer.toString(), true, true);
/*  693 */       this._printer.unindent();
/*  694 */       if (this._indenting) {
/*  695 */         state.afterElement = true;
/*      */       }
/*      */     } 
/*  698 */     this.fStrBuffer.setLength(0);
/*  699 */     state.afterComment = true;
/*  700 */     state.afterElement = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startCDATA() {
/*  708 */     ElementState state = getElementState();
/*  709 */     state.doCData = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endCDATA() {
/*  717 */     ElementState state = getElementState();
/*  718 */     state.doCData = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startNonEscaping() {
/*  726 */     ElementState state = getElementState();
/*  727 */     state.unescaped = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endNonEscaping() {
/*  735 */     ElementState state = getElementState();
/*  736 */     state.unescaped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startPreserving() {
/*  744 */     ElementState state = getElementState();
/*  745 */     state.preserveSpace = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endPreserving() {
/*  753 */     ElementState state = getElementState();
/*  754 */     state.preserveSpace = false;
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
/*      */   public void endDocument() throws SAXException {
/*      */     try {
/*  772 */       serializePreRoot();
/*      */       
/*  774 */       this._printer.flush();
/*  775 */     } catch (IOException except) {
/*  776 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startEntity(String name) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endEntity(String name) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentLocator(Locator locator) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void skippedEntity(String name) throws SAXException {
/*      */     try {
/*  808 */       endCDATA();
/*  809 */       content();
/*  810 */       this._printer.printText('&');
/*  811 */       this._printer.printText(name);
/*  812 */       this._printer.printText(';');
/*  813 */     } catch (IOException except) {
/*  814 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*  822 */     if (this._prefixes == null)
/*  823 */       this._prefixes = new HashMap<>(); 
/*  824 */     this._prefixes.put(uri, (prefix == null) ? "" : prefix);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endPrefixMapping(String prefix) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void startDTD(String name, String publicId, String systemId) throws SAXException {
/*      */     try {
/*  843 */       this._printer.enterDTD();
/*  844 */       this._docTypePublicId = publicId;
/*  845 */       this._docTypeSystemId = systemId;
/*      */     }
/*  847 */     catch (IOException except) {
/*  848 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endDTD() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void elementDecl(String name, String model) throws SAXException {
/*      */     try {
/*  863 */       this._printer.enterDTD();
/*  864 */       this._printer.printText("<!ELEMENT ");
/*  865 */       this._printer.printText(name);
/*  866 */       this._printer.printText(' ');
/*  867 */       this._printer.printText(model);
/*  868 */       this._printer.printText('>');
/*  869 */       if (this._indenting)
/*  870 */         this._printer.breakLine(); 
/*  871 */     } catch (IOException except) {
/*  872 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value) throws SAXException {
/*      */     try {
/*  882 */       this._printer.enterDTD();
/*  883 */       this._printer.printText("<!ATTLIST ");
/*  884 */       this._printer.printText(eName);
/*  885 */       this._printer.printText(' ');
/*  886 */       this._printer.printText(aName);
/*  887 */       this._printer.printText(' ');
/*  888 */       this._printer.printText(type);
/*  889 */       if (valueDefault != null) {
/*  890 */         this._printer.printText(' ');
/*  891 */         this._printer.printText(valueDefault);
/*      */       } 
/*  893 */       if (value != null) {
/*  894 */         this._printer.printText(" \"");
/*  895 */         printEscaped(value);
/*  896 */         this._printer.printText('"');
/*      */       } 
/*  898 */       this._printer.printText('>');
/*  899 */       if (this._indenting)
/*  900 */         this._printer.breakLine(); 
/*  901 */     } catch (IOException except) {
/*  902 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void internalEntityDecl(String name, String value) throws SAXException {
/*      */     try {
/*  911 */       this._printer.enterDTD();
/*  912 */       this._printer.printText("<!ENTITY ");
/*  913 */       this._printer.printText(name);
/*  914 */       this._printer.printText(" \"");
/*  915 */       printEscaped(value);
/*  916 */       this._printer.printText("\">");
/*  917 */       if (this._indenting)
/*  918 */         this._printer.breakLine(); 
/*  919 */     } catch (IOException except) {
/*  920 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {
/*      */     try {
/*  929 */       this._printer.enterDTD();
/*  930 */       unparsedEntityDecl(name, publicId, systemId, null);
/*  931 */     } catch (IOException except) {
/*  932 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
/*      */     try {
/*  942 */       this._printer.enterDTD();
/*  943 */       if (publicId == null) {
/*  944 */         this._printer.printText("<!ENTITY ");
/*  945 */         this._printer.printText(name);
/*  946 */         this._printer.printText(" SYSTEM ");
/*  947 */         printDoctypeURL(systemId);
/*      */       } else {
/*  949 */         this._printer.printText("<!ENTITY ");
/*  950 */         this._printer.printText(name);
/*  951 */         this._printer.printText(" PUBLIC ");
/*  952 */         printDoctypeURL(publicId);
/*  953 */         this._printer.printText(' ');
/*  954 */         printDoctypeURL(systemId);
/*      */       } 
/*  956 */       if (notationName != null) {
/*  957 */         this._printer.printText(" NDATA ");
/*  958 */         this._printer.printText(notationName);
/*      */       } 
/*  960 */       this._printer.printText('>');
/*  961 */       if (this._indenting)
/*  962 */         this._printer.breakLine(); 
/*  963 */     } catch (IOException except) {
/*  964 */       throw new SAXException(except);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notationDecl(String name, String publicId, String systemId) throws SAXException {
/*      */     try {
/*  973 */       this._printer.enterDTD();
/*  974 */       if (publicId != null) {
/*  975 */         this._printer.printText("<!NOTATION ");
/*  976 */         this._printer.printText(name);
/*  977 */         this._printer.printText(" PUBLIC ");
/*  978 */         printDoctypeURL(publicId);
/*  979 */         if (systemId != null) {
/*  980 */           this._printer.printText(' ');
/*  981 */           printDoctypeURL(systemId);
/*      */         } 
/*      */       } else {
/*  984 */         this._printer.printText("<!NOTATION ");
/*  985 */         this._printer.printText(name);
/*  986 */         this._printer.printText(" SYSTEM ");
/*  987 */         printDoctypeURL(systemId);
/*      */       } 
/*  989 */       this._printer.printText('>');
/*  990 */       if (this._indenting)
/*  991 */         this._printer.breakLine(); 
/*  992 */     } catch (IOException except) {
/*  993 */       throw new SAXException(except);
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
/*      */   protected void serializeNode(Node node) throws IOException {
/*      */     String text;
/*      */     Node node1;
/*      */     DocumentType docType;
/*      */     Node child;
/* 1016 */     this.fCurrentNode = node;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1021 */     switch (node.getNodeType()) {
/*      */ 
/*      */       
/*      */       case 3:
/* 1025 */         text = node.getNodeValue();
/* 1026 */         if (text != null) {
/* 1027 */           if (this.fDOMFilter != null && (this.fDOMFilter
/* 1028 */             .getWhatToShow() & 0x4) != 0) {
/* 1029 */             short code = this.fDOMFilter.acceptNode(node);
/* 1030 */             switch (code) {
/*      */               case 2:
/*      */               case 3:
/*      */                 break;
/*      */             } 
/*      */             
/* 1036 */             characters(text);
/*      */             
/*      */             break;
/*      */           } 
/* 1040 */           if (!this._indenting || (getElementState()).preserveSpace || text
/* 1041 */             .replace('\n', ' ').trim().length() != 0) {
/* 1042 */             characters(text);
/*      */           }
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/* 1049 */         text = node.getNodeValue();
/* 1050 */         if ((this.features & 0x8) != 0) {
/* 1051 */           if (text != null) {
/* 1052 */             if (this.fDOMFilter != null && (this.fDOMFilter
/* 1053 */               .getWhatToShow() & 0x8) != 0) {
/*      */ 
/*      */               
/* 1056 */               short code = this.fDOMFilter.acceptNode(node);
/* 1057 */               switch (code) {
/*      */                 case 2:
/*      */                 case 3:
/*      */                   return;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             } 
/* 1070 */             startCDATA();
/* 1071 */             characters(text);
/* 1072 */             endCDATA();
/*      */           } 
/*      */           break;
/*      */         } 
/* 1076 */         characters(text);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 1083 */         if (!this._format.getOmitComments()) {
/* 1084 */           text = node.getNodeValue();
/* 1085 */           if (text != null) {
/*      */             
/* 1087 */             if (this.fDOMFilter != null && (this.fDOMFilter
/* 1088 */               .getWhatToShow() & 0x80) != 0) {
/* 1089 */               short code = this.fDOMFilter.acceptNode(node);
/* 1090 */               switch (code) {
/*      */                 case 2:
/*      */                 case 3:
/*      */                   return;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             } 
/* 1101 */             comment(text);
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 1110 */         endCDATA();
/* 1111 */         content();
/*      */         
/* 1113 */         if ((this.features & 0x4) != 0 || node
/* 1114 */           .getFirstChild() == null) {
/* 1115 */           if (this.fDOMFilter != null && (this.fDOMFilter
/* 1116 */             .getWhatToShow() & 0x10) != 0) {
/* 1117 */             Node node2; short code = this.fDOMFilter.acceptNode(node);
/* 1118 */             switch (code) {
/*      */               case 2:
/*      */                 return;
/*      */               
/*      */               case 3:
/* 1123 */                 node2 = node.getFirstChild();
/* 1124 */                 while (node2 != null) {
/* 1125 */                   serializeNode(node2);
/* 1126 */                   node2 = node2.getNextSibling();
/*      */                 } 
/*      */                 return;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           } 
/* 1136 */           checkUnboundNamespacePrefixedNode(node);
/*      */           
/* 1138 */           this._printer.printText("&");
/* 1139 */           this._printer.printText(node.getNodeName());
/* 1140 */           this._printer.printText(";");
/*      */           break;
/*      */         } 
/* 1143 */         node1 = node.getFirstChild();
/* 1144 */         while (node1 != null) {
/* 1145 */           serializeNode(node1);
/* 1146 */           node1 = node1.getNextSibling();
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/* 1155 */         if (this.fDOMFilter != null && (this.fDOMFilter
/* 1156 */           .getWhatToShow() & 0x40) != 0) {
/* 1157 */           short code = this.fDOMFilter.acceptNode(node);
/* 1158 */           switch (code) {
/*      */             case 2:
/*      */             case 3:
/*      */               return;
/*      */           } 
/*      */ 
/*      */ 
/*      */         
/*      */         } 
/* 1167 */         processingInstructionIO(node.getNodeName(), node.getNodeValue());
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 1172 */         if (this.fDOMFilter != null && (this.fDOMFilter
/* 1173 */           .getWhatToShow() & 0x1) != 0) {
/* 1174 */           Node node2; short code = this.fDOMFilter.acceptNode(node);
/* 1175 */           switch (code) {
/*      */             case 2:
/*      */               return;
/*      */             
/*      */             case 3:
/* 1180 */               node2 = node.getFirstChild();
/* 1181 */               while (node2 != null) {
/* 1182 */                 serializeNode(node2);
/* 1183 */                 node2 = node2.getNextSibling();
/*      */               } 
/*      */               return;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         } 
/* 1192 */         serializeElement((Element)node);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/* 1203 */         serializeDocument();
/*      */ 
/*      */ 
/*      */         
/* 1207 */         docType = ((Document)node).getDoctype();
/* 1208 */         if (docType != null) {
/*      */           
/* 1210 */           DOMImplementation domImpl = ((Document)node).getImplementation();
/*      */ 
/*      */           
/*      */           try {
/* 1214 */             this._printer.enterDTD();
/* 1215 */             this._docTypePublicId = docType.getPublicId();
/* 1216 */             this._docTypeSystemId = docType.getSystemId();
/* 1217 */             String internal = docType.getInternalSubset();
/* 1218 */             if (internal != null && internal.length() > 0)
/* 1219 */               this._printer.printText(internal); 
/* 1220 */             endDTD();
/*      */           
/*      */           }
/* 1223 */           catch (NoSuchMethodError nsme) {
/* 1224 */             Class<?> docTypeClass = docType.getClass();
/*      */             
/* 1226 */             String docTypePublicId = null;
/* 1227 */             String docTypeSystemId = null;
/*      */             try {
/* 1229 */               Method getPublicId = docTypeClass.getMethod("getPublicId", (Class[])null);
/* 1230 */               if (getPublicId.getReturnType().equals(String.class)) {
/* 1231 */                 docTypePublicId = (String)getPublicId.invoke(docType, (Object[])null);
/*      */               }
/*      */             }
/* 1234 */             catch (Exception exception) {}
/*      */ 
/*      */             
/*      */             try {
/* 1238 */               Method getSystemId = docTypeClass.getMethod("getSystemId", (Class[])null);
/* 1239 */               if (getSystemId.getReturnType().equals(String.class)) {
/* 1240 */                 docTypeSystemId = (String)getSystemId.invoke(docType, (Object[])null);
/*      */               }
/*      */             }
/* 1243 */             catch (Exception exception) {}
/*      */ 
/*      */             
/* 1246 */             this._printer.enterDTD();
/* 1247 */             this._docTypePublicId = docTypePublicId;
/* 1248 */             this._docTypeSystemId = docTypeSystemId;
/* 1249 */             endDTD();
/*      */           } 
/*      */           
/* 1252 */           serializeDTD(docType.getName());
/*      */         } 
/*      */         
/* 1255 */         this._started = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 11:
/* 1265 */         child = node.getFirstChild();
/* 1266 */         while (child != null) {
/* 1267 */           serializeNode(child);
/* 1268 */           child = child.getNextSibling();
/*      */         } 
/*      */         break;
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
/*      */   protected void serializeDocument() throws IOException {
/* 1284 */     String dtd = this._printer.leaveDTD();
/* 1285 */     if (!this._started)
/*      */     {
/* 1287 */       if (!this._format.getOmitXMLDeclaration()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1292 */         StringBuffer buffer = new StringBuffer("<?xml version=\"");
/* 1293 */         if (this._format.getVersion() != null) {
/* 1294 */           buffer.append(this._format.getVersion());
/*      */         } else {
/* 1296 */           buffer.append("1.0");
/* 1297 */         }  buffer.append('"');
/* 1298 */         String format_encoding = this._format.getEncoding();
/* 1299 */         if (format_encoding != null) {
/* 1300 */           buffer.append(" encoding=\"");
/* 1301 */           buffer.append(format_encoding);
/* 1302 */           buffer.append('"');
/*      */         } 
/* 1304 */         if (this._format.getStandalone() && this._docTypeSystemId == null && this._docTypePublicId == null)
/*      */         {
/* 1306 */           buffer.append(" standalone=\"yes\""); } 
/* 1307 */         buffer.append("?>");
/* 1308 */         this._printer.printText(buffer);
/* 1309 */         this._printer.breakLine();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1314 */     serializePreRoot();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void serializeDTD(String name) throws IOException {
/* 1322 */     String dtd = this._printer.leaveDTD();
/* 1323 */     if (!this._format.getOmitDocumentType()) {
/* 1324 */       if (this._docTypeSystemId != null) {
/*      */ 
/*      */ 
/*      */         
/* 1328 */         this._printer.printText("<!DOCTYPE ");
/* 1329 */         this._printer.printText(name);
/* 1330 */         if (this._docTypePublicId != null) {
/* 1331 */           this._printer.printText(" PUBLIC ");
/* 1332 */           printDoctypeURL(this._docTypePublicId);
/* 1333 */           if (this._indenting) {
/* 1334 */             this._printer.breakLine();
/* 1335 */             for (int i = 0; i < 18 + name.length(); i++)
/* 1336 */               this._printer.printText(" "); 
/*      */           } else {
/* 1338 */             this._printer.printText(" ");
/* 1339 */           }  printDoctypeURL(this._docTypeSystemId);
/*      */         } else {
/* 1341 */           this._printer.printText(" SYSTEM ");
/* 1342 */           printDoctypeURL(this._docTypeSystemId);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1347 */         if (dtd != null && dtd.length() > 0) {
/* 1348 */           this._printer.printText(" [");
/* 1349 */           printText(dtd, true, true);
/* 1350 */           this._printer.printText(']');
/*      */         } 
/*      */         
/* 1353 */         this._printer.printText(">");
/* 1354 */         this._printer.breakLine();
/* 1355 */       } else if (dtd != null && dtd.length() > 0) {
/* 1356 */         this._printer.printText("<!DOCTYPE ");
/* 1357 */         this._printer.printText(name);
/* 1358 */         this._printer.printText(" [");
/* 1359 */         printText(dtd, true, true);
/* 1360 */         this._printer.printText("]>");
/* 1361 */         this._printer.breakLine();
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
/*      */   protected ElementState content() throws IOException {
/* 1382 */     ElementState state = getElementState();
/* 1383 */     if (!isDocumentState()) {
/*      */       
/* 1385 */       if (state.inCData && !state.doCData) {
/* 1386 */         this._printer.printText("]]>");
/* 1387 */         state.inCData = false;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1392 */       if (state.empty) {
/* 1393 */         this._printer.printText('>');
/* 1394 */         state.empty = false;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1399 */       state.afterElement = false;
/*      */ 
/*      */ 
/*      */       
/* 1403 */       state.afterComment = false;
/*      */     } 
/* 1405 */     return state;
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
/*      */   protected void characters(String text) throws IOException {
/* 1426 */     ElementState state = content();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1431 */     if (state.inCData || state.doCData) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1438 */       if (!state.inCData) {
/* 1439 */         this._printer.printText("<![CDATA[");
/* 1440 */         state.inCData = true;
/*      */       } 
/* 1442 */       int saveIndent = this._printer.getNextIndent();
/* 1443 */       this._printer.setNextIndent(0);
/* 1444 */       printCDATAText(text);
/* 1445 */       this._printer.setNextIndent(saveIndent);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1451 */     else if (state.preserveSpace) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1456 */       int saveIndent = this._printer.getNextIndent();
/* 1457 */       this._printer.setNextIndent(0);
/* 1458 */       printText(text, true, state.unescaped);
/* 1459 */       this._printer.setNextIndent(saveIndent);
/*      */     } else {
/* 1461 */       printText(text, false, state.unescaped);
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
/*      */   protected abstract String getEntityRef(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void serializeElement(Element paramElement) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void serializePreRoot() throws IOException {
/* 1506 */     if (this._preRoot != null) {
/* 1507 */       for (int i = 0; i < this._preRoot.size(); i++) {
/* 1508 */         printText(this._preRoot.elementAt(i), true, true);
/* 1509 */         if (this._indenting)
/* 1510 */           this._printer.breakLine(); 
/*      */       } 
/* 1512 */       this._preRoot.removeAllElements();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void printCDATAText(String text) throws IOException {
/* 1522 */     int length = text.length();
/*      */ 
/*      */     
/* 1525 */     for (int index = 0; index < length; index++) {
/* 1526 */       char ch = text.charAt(index);
/* 1527 */       if (ch == ']' && index + 2 < length && text
/*      */         
/* 1529 */         .charAt(index + 1) == ']' && text
/* 1530 */         .charAt(index + 2) == '>') {
/* 1531 */         if (this.fDOMErrorHandler != null)
/*      */         {
/*      */           
/* 1534 */           if ((this.features & 0x10) == 0) {
/* 1535 */             String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "EndingCDATA", null);
/*      */ 
/*      */ 
/*      */             
/* 1539 */             if ((this.features & 0x2) != 0) {
/*      */               
/* 1541 */               modifyDOMError(msg, (short)3, "wf-invalid-character", this.fCurrentNode);
/* 1542 */               this.fDOMErrorHandler.handleError(this.fDOMError);
/* 1543 */               throw new LSException((short)82, msg);
/*      */             } 
/*      */ 
/*      */             
/* 1547 */             modifyDOMError(msg, (short)2, "cdata-section-not-splitted", this.fCurrentNode);
/* 1548 */             if (!this.fDOMErrorHandler.handleError(this.fDOMError)) {
/* 1549 */               throw new LSException((short)82, msg);
/*      */             
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 1555 */             String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "SplittingCDATA", null);
/*      */ 
/*      */ 
/*      */             
/* 1559 */             modifyDOMError(msg, (short)1, null, this.fCurrentNode);
/*      */ 
/*      */ 
/*      */             
/* 1563 */             this.fDOMErrorHandler.handleError(this.fDOMError);
/*      */           } 
/*      */         }
/*      */         
/* 1567 */         this._printer.printText("]]]]><![CDATA[>");
/* 1568 */         index += 2;
/*      */ 
/*      */       
/*      */       }
/* 1572 */       else if (!XMLChar.isValid(ch)) {
/*      */         
/* 1574 */         if (++index < length) {
/* 1575 */           surrogates(ch, text.charAt(index));
/*      */         } else {
/*      */           
/* 1578 */           fatalError("The character '" + ch + "' is an invalid XML character");
/*      */         }
/*      */       
/*      */       }
/* 1582 */       else if ((ch >= ' ' && this._encodingInfo.isPrintable(ch) && ch != '÷') || ch == '\n' || ch == '\r' || ch == '\t') {
/*      */         
/* 1584 */         this._printer.printText(ch);
/*      */       }
/*      */       else {
/*      */         
/* 1588 */         this._printer.printText("]]>&#x");
/* 1589 */         this._printer.printText(Integer.toHexString(ch));
/* 1590 */         this._printer.printText(";<![CDATA[");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void surrogates(int high, int low) throws IOException {
/* 1598 */     if (XMLChar.isHighSurrogate(high)) {
/* 1599 */       if (!XMLChar.isLowSurrogate(low)) {
/*      */         
/* 1601 */         fatalError("The character '" + (char)low + "' is an invalid XML character");
/*      */       } else {
/*      */         
/* 1604 */         int supplemental = XMLChar.supplemental((char)high, (char)low);
/* 1605 */         if (!XMLChar.isValid(supplemental)) {
/*      */           
/* 1607 */           fatalError("The character '" + (char)supplemental + "' is an invalid XML character");
/*      */         
/*      */         }
/* 1610 */         else if ((content()).inCData) {
/* 1611 */           this._printer.printText("]]>&#x");
/* 1612 */           this._printer.printText(Integer.toHexString(supplemental));
/* 1613 */           this._printer.printText(";<![CDATA[");
/*      */         } else {
/*      */           
/* 1616 */           printHex(supplemental);
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1621 */       fatalError("The character '" + (char)high + "' is an invalid XML character");
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
/*      */   protected void printText(char[] chars, int start, int length, boolean preserveSpace, boolean unescaped) throws IOException {
/* 1646 */     if (preserveSpace) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1651 */       while (length-- > 0) {
/* 1652 */         char ch = chars[start];
/* 1653 */         start++;
/* 1654 */         if (ch == '\n' || ch == '\r' || unescaped) {
/* 1655 */           this._printer.printText(ch); continue;
/*      */         } 
/* 1657 */         printEscaped(ch);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1665 */       while (length-- > 0) {
/* 1666 */         char ch = chars[start];
/* 1667 */         start++;
/* 1668 */         if (ch == ' ' || ch == '\f' || ch == '\t' || ch == '\n' || ch == '\r') {
/* 1669 */           this._printer.printSpace(); continue;
/* 1670 */         }  if (unescaped) {
/* 1671 */           this._printer.printText(ch); continue;
/*      */         } 
/* 1673 */         printEscaped(ch);
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
/*      */   protected void printText(String text, boolean preserveSpace, boolean unescaped) throws IOException {
/* 1685 */     if (preserveSpace) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1690 */       for (int index = 0; index < text.length(); index++) {
/* 1691 */         char ch = text.charAt(index);
/* 1692 */         if (ch == '\n' || ch == '\r' || unescaped) {
/* 1693 */           this._printer.printText(ch);
/*      */         } else {
/* 1695 */           printEscaped(ch);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1703 */       for (int index = 0; index < text.length(); index++) {
/* 1704 */         char ch = text.charAt(index);
/* 1705 */         if (ch == ' ' || ch == '\f' || ch == '\t' || ch == '\n' || ch == '\r') {
/* 1706 */           this._printer.printSpace();
/* 1707 */         } else if (unescaped) {
/* 1708 */           this._printer.printText(ch);
/*      */         } else {
/* 1710 */           printEscaped(ch);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void printDoctypeURL(String url) throws IOException {
/* 1728 */     this._printer.printText('"');
/* 1729 */     for (int i = 0; i < url.length(); i++) {
/* 1730 */       if (url.charAt(i) == '"' || url.charAt(i) < ' ' || url.charAt(i) > '') {
/* 1731 */         this._printer.printText('%');
/* 1732 */         this._printer.printText(Integer.toHexString(url.charAt(i)));
/*      */       } else {
/* 1734 */         this._printer.printText(url.charAt(i));
/*      */       } 
/* 1736 */     }  this._printer.printText('"');
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
/*      */   protected void printEscaped(int ch) throws IOException {
/* 1748 */     String charRef = getEntityRef(ch);
/* 1749 */     if (charRef != null) {
/* 1750 */       this._printer.printText('&');
/* 1751 */       this._printer.printText(charRef);
/* 1752 */       this._printer.printText(';');
/* 1753 */     } else if ((ch >= 32 && this._encodingInfo.isPrintable((char)ch) && ch != 247) || ch == 10 || ch == 13 || ch == 9) {
/*      */ 
/*      */ 
/*      */       
/* 1757 */       if (ch < 65536) {
/* 1758 */         this._printer.printText((char)ch);
/*      */       } else {
/* 1760 */         this._printer.printText((char)((ch - 65536 >> 10) + 55296));
/* 1761 */         this._printer.printText((char)((ch - 65536 & 0x3FF) + 56320));
/*      */       } 
/*      */     } else {
/* 1764 */       printHex(ch);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void printHex(int ch) throws IOException {
/* 1772 */     this._printer.printText("&#x");
/* 1773 */     this._printer.printText(Integer.toHexString(ch));
/* 1774 */     this._printer.printText(';');
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
/*      */   protected void printEscaped(String source) throws IOException {
/* 1790 */     for (int i = 0; i < source.length(); i++) {
/* 1791 */       int ch = source.charAt(i);
/* 1792 */       if ((ch & 0xFC00) == 55296 && i + 1 < source.length()) {
/* 1793 */         int lowch = source.charAt(i + 1);
/* 1794 */         if ((lowch & 0xFC00) == 56320) {
/* 1795 */           ch = 65536 + (ch - 55296 << 10) + lowch - 56320;
/* 1796 */           i++;
/*      */         } 
/*      */       } 
/* 1799 */       printEscaped(ch);
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
/*      */   protected ElementState getElementState() {
/* 1816 */     return this._elementStates[this._elementStateCount];
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
/*      */   protected ElementState enterElementState(String namespaceURI, String localName, String rawName, boolean preserveSpace) {
/* 1832 */     if (this._elementStateCount + 1 == this._elementStates.length) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1837 */       ElementState[] newStates = new ElementState[this._elementStates.length + 10]; int i;
/* 1838 */       for (i = 0; i < this._elementStates.length; i++)
/* 1839 */         newStates[i] = this._elementStates[i]; 
/* 1840 */       for (i = this._elementStates.length; i < newStates.length; i++)
/* 1841 */         newStates[i] = new ElementState(); 
/* 1842 */       this._elementStates = newStates;
/*      */     } 
/*      */     
/* 1845 */     this._elementStateCount++;
/* 1846 */     ElementState state = this._elementStates[this._elementStateCount];
/* 1847 */     state.namespaceURI = namespaceURI;
/* 1848 */     state.localName = localName;
/* 1849 */     state.rawName = rawName;
/* 1850 */     state.preserveSpace = preserveSpace;
/* 1851 */     state.empty = true;
/* 1852 */     state.afterElement = false;
/* 1853 */     state.afterComment = false;
/* 1854 */     state.doCData = state.inCData = false;
/* 1855 */     state.unescaped = false;
/* 1856 */     state.prefixes = this._prefixes;
/*      */     
/* 1858 */     this._prefixes = null;
/* 1859 */     return state;
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
/*      */   protected ElementState leaveElementState() {
/* 1872 */     if (this._elementStateCount > 0) {
/*      */       
/* 1874 */       this._prefixes = null;
/*      */       
/* 1876 */       this._elementStateCount--;
/* 1877 */       return this._elementStates[this._elementStateCount];
/*      */     } 
/* 1879 */     String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "Internal", null);
/* 1880 */     throw new IllegalStateException(msg);
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
/*      */   protected boolean isDocumentState() {
/* 1894 */     return (this._elementStateCount == 0);
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
/*      */   protected String getPrefix(String namespaceURI) {
/* 1910 */     if (this._prefixes != null) {
/* 1911 */       String prefix = this._prefixes.get(namespaceURI);
/* 1912 */       if (prefix != null)
/* 1913 */         return prefix; 
/*      */     } 
/* 1915 */     if (this._elementStateCount == 0) {
/* 1916 */       return null;
/*      */     }
/* 1918 */     for (int i = this._elementStateCount; i > 0; i--) {
/* 1919 */       if ((this._elementStates[i]).prefixes != null) {
/* 1920 */         String prefix = (this._elementStates[i]).prefixes.get(namespaceURI);
/* 1921 */         if (prefix != null) {
/* 1922 */           return prefix;
/*      */         }
/*      */       } 
/*      */     } 
/* 1926 */     return null;
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
/*      */   protected DOMError modifyDOMError(String message, short severity, String type, Node node) {
/* 1938 */     this.fDOMError.reset();
/* 1939 */     this.fDOMError.fMessage = message;
/* 1940 */     this.fDOMError.fType = type;
/* 1941 */     this.fDOMError.fSeverity = severity;
/* 1942 */     this.fDOMError.fLocator = new DOMLocatorImpl(-1, -1, -1, node, null);
/* 1943 */     return this.fDOMError;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fatalError(String message) throws IOException {
/* 1949 */     if (this.fDOMErrorHandler != null) {
/* 1950 */       modifyDOMError(message, (short)3, null, this.fCurrentNode);
/* 1951 */       this.fDOMErrorHandler.handleError(this.fDOMError);
/*      */     } else {
/*      */       
/* 1954 */       throw new IOException(message);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkUnboundNamespacePrefixedNode(Node node) throws IOException {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/BaseMarkupSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */