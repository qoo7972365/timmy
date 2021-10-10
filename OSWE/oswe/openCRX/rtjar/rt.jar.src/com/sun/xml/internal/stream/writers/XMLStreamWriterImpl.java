/*      */ package com.sun.xml.internal.stream.writers;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.PropertyManager;
/*      */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.xml.internal.stream.util.ReadOnlyIterator;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Writer;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CharsetEncoder;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamWriter;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class XMLStreamWriterImpl
/*      */   extends AbstractMap
/*      */   implements XMLStreamWriter
/*      */ {
/*      */   public static final String START_COMMENT = "<!--";
/*      */   public static final String END_COMMENT = "-->";
/*      */   public static final String DEFAULT_ENCODING = " encoding=\"utf-8\"";
/*      */   public static final String DEFAULT_XMLDECL = "<?xml version=\"1.0\" ?>";
/*      */   public static final String DEFAULT_XML_VERSION = "1.0";
/*      */   public static final char CLOSE_START_TAG = '>';
/*      */   public static final char OPEN_START_TAG = '<';
/*      */   public static final String OPEN_END_TAG = "</";
/*      */   public static final char CLOSE_END_TAG = '>';
/*      */   public static final String START_CDATA = "<![CDATA[";
/*      */   public static final String END_CDATA = "]]>";
/*      */   public static final String CLOSE_EMPTY_ELEMENT = "/>";
/*      */   public static final String SPACE = " ";
/*      */   public static final String UTF_8 = "UTF-8";
/*      */   public static final String OUTPUTSTREAM_PROPERTY = "sjsxp-outputstream";
/*      */   boolean fEscapeCharacters = true;
/*      */   private boolean fIsRepairingNamespace = false;
/*      */   private Writer fWriter;
/*  113 */   private OutputStream fOutputStream = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ArrayList fAttributeCache;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ArrayList fNamespaceDecls;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   private NamespaceContextImpl fNamespaceContext = null;
/*      */   
/*  131 */   private NamespaceSupport fInternalNamespaceContext = null;
/*      */   
/*  133 */   private Random fPrefixGen = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  138 */   private PropertyManager fPropertyManager = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fStartTagOpened = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fReuse;
/*      */ 
/*      */ 
/*      */   
/*  150 */   private SymbolTable fSymbolTable = new SymbolTable();
/*      */   
/*  152 */   private ElementStack fElementStack = new ElementStack();
/*      */   
/*  154 */   private final String DEFAULT_PREFIX = this.fSymbolTable.addSymbol("");
/*      */   
/*  156 */   private final ReadOnlyIterator fReadOnlyIterator = new ReadOnlyIterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  164 */   private CharsetEncoder fEncoder = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  171 */   HashMap fAttrNamespace = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamWriterImpl(OutputStream outputStream, PropertyManager props) throws IOException {
/*  187 */     this(new OutputStreamWriter(outputStream), props);
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
/*      */   public XMLStreamWriterImpl(OutputStream outputStream, String encoding, PropertyManager props) throws IOException {
/*  199 */     this(new StreamResult(outputStream), encoding, props);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamWriterImpl(Writer writer, PropertyManager props) throws IOException {
/*  210 */     this(new StreamResult(writer), (String)null, props);
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
/*      */   public XMLStreamWriterImpl(StreamResult sr, String encoding, PropertyManager props) throws IOException {
/*  222 */     setOutput(sr, encoding);
/*  223 */     this.fPropertyManager = props;
/*  224 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init() {
/*  232 */     this.fReuse = false;
/*  233 */     this.fNamespaceDecls = new ArrayList();
/*  234 */     this.fPrefixGen = new Random();
/*  235 */     this.fAttributeCache = new ArrayList();
/*  236 */     this.fInternalNamespaceContext = new NamespaceSupport();
/*  237 */     this.fInternalNamespaceContext.reset();
/*  238 */     this.fNamespaceContext = new NamespaceContextImpl();
/*  239 */     this.fNamespaceContext.internalContext = this.fInternalNamespaceContext;
/*      */ 
/*      */     
/*  242 */     Boolean ob = (Boolean)this.fPropertyManager.getProperty("javax.xml.stream.isRepairingNamespaces");
/*  243 */     this.fIsRepairingNamespace = ob.booleanValue();
/*  244 */     ob = (Boolean)this.fPropertyManager.getProperty("escapeCharacters");
/*  245 */     setEscapeCharacters(ob.booleanValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  254 */     reset(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void reset(boolean resetProperties) {
/*  264 */     if (!this.fReuse) {
/*  265 */       throw new IllegalStateException("close() Must be called before calling reset()");
/*      */     }
/*      */ 
/*      */     
/*  269 */     this.fReuse = false;
/*  270 */     this.fNamespaceDecls.clear();
/*  271 */     this.fAttributeCache.clear();
/*      */ 
/*      */     
/*  274 */     this.fElementStack.clear();
/*  275 */     this.fInternalNamespaceContext.reset();
/*      */     
/*  277 */     this.fStartTagOpened = false;
/*  278 */     this.fNamespaceContext.userContext = null;
/*      */     
/*  280 */     if (resetProperties) {
/*  281 */       Boolean ob = (Boolean)this.fPropertyManager.getProperty("javax.xml.stream.isRepairingNamespaces");
/*  282 */       this.fIsRepairingNamespace = ob.booleanValue();
/*  283 */       ob = (Boolean)this.fPropertyManager.getProperty("escapeCharacters");
/*  284 */       setEscapeCharacters(ob.booleanValue());
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
/*      */   public void setOutput(StreamResult sr, String encoding) throws IOException {
/*  298 */     if (sr.getOutputStream() != null) {
/*  299 */       setOutputUsingStream(sr.getOutputStream(), encoding);
/*      */     }
/*  301 */     else if (sr.getWriter() != null) {
/*  302 */       setOutputUsingWriter(sr.getWriter());
/*      */     }
/*  304 */     else if (sr.getSystemId() != null) {
/*  305 */       setOutputUsingStream(new FileOutputStream(sr.getSystemId()), encoding);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setOutputUsingWriter(Writer writer) throws IOException {
/*  313 */     this.fWriter = writer;
/*      */     
/*  315 */     if (writer instanceof OutputStreamWriter) {
/*  316 */       String charset = ((OutputStreamWriter)writer).getEncoding();
/*  317 */       if (charset != null && !charset.equalsIgnoreCase("utf-8")) {
/*  318 */         this.fEncoder = Charset.forName(charset).newEncoder();
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
/*      */   private void setOutputUsingStream(OutputStream os, String encoding) throws IOException {
/*  333 */     this.fOutputStream = os;
/*      */     
/*  335 */     if (encoding != null) {
/*  336 */       if (encoding.equalsIgnoreCase("utf-8")) {
/*  337 */         this.fWriter = new UTF8OutputStreamWriter(os);
/*      */       } else {
/*      */         
/*  340 */         this.fWriter = new XMLWriter(new OutputStreamWriter(os, encoding));
/*  341 */         this.fEncoder = Charset.forName(encoding).newEncoder();
/*      */       } 
/*      */     } else {
/*  344 */       encoding = SecuritySupport.getSystemProperty("file.encoding");
/*  345 */       if (encoding != null && encoding.equalsIgnoreCase("utf-8")) {
/*  346 */         this.fWriter = new UTF8OutputStreamWriter(os);
/*      */       } else {
/*  348 */         this.fWriter = new XMLWriter(new OutputStreamWriter(os));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canReuse() {
/*  358 */     return this.fReuse;
/*      */   }
/*      */   
/*      */   public void setEscapeCharacters(boolean escape) {
/*  362 */     this.fEscapeCharacters = escape;
/*      */   }
/*      */   
/*      */   public boolean getEscapeCharacters() {
/*  366 */     return this.fEscapeCharacters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws XMLStreamException {
/*  373 */     if (this.fWriter != null) {
/*      */       
/*      */       try {
/*  376 */         this.fWriter.flush();
/*  377 */       } catch (IOException e) {
/*  378 */         throw new XMLStreamException(e);
/*      */       } 
/*      */     }
/*  381 */     this.fWriter = null;
/*  382 */     this.fOutputStream = null;
/*  383 */     this.fNamespaceDecls.clear();
/*  384 */     this.fAttributeCache.clear();
/*  385 */     this.fElementStack.clear();
/*  386 */     this.fInternalNamespaceContext.reset();
/*  387 */     this.fReuse = true;
/*  388 */     this.fStartTagOpened = false;
/*  389 */     this.fNamespaceContext.userContext = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() throws XMLStreamException {
/*      */     try {
/*  397 */       this.fWriter.flush();
/*  398 */     } catch (IOException e) {
/*  399 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NamespaceContext getNamespaceContext() {
/*  409 */     return this.fNamespaceContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPrefix(String uri) throws XMLStreamException {
/*  420 */     return this.fNamespaceContext.getPrefix(uri);
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
/*      */   public Object getProperty(String str) throws IllegalArgumentException {
/*  432 */     if (str == null) {
/*  433 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  436 */     if (!this.fPropertyManager.containsProperty(str)) {
/*  437 */       throw new IllegalArgumentException("Property '" + str + "' is not supported");
/*      */     }
/*      */ 
/*      */     
/*  441 */     return this.fPropertyManager.getProperty(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/*  450 */     if (uri != null) {
/*  451 */       uri = this.fSymbolTable.addSymbol(uri);
/*      */     }
/*      */     
/*  454 */     if (this.fIsRepairingNamespace) {
/*  455 */       if (isDefaultNamespace(uri)) {
/*      */         return;
/*      */       }
/*      */       
/*  459 */       QName qname = new QName();
/*  460 */       qname.setValues(this.DEFAULT_PREFIX, "xmlns", null, uri);
/*  461 */       this.fNamespaceDecls.add(qname);
/*      */     } else {
/*  463 */       this.fInternalNamespaceContext.declarePrefix(this.DEFAULT_PREFIX, uri);
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
/*      */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/*  484 */     this.fNamespaceContext.userContext = namespaceContext;
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
/*      */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/*  498 */     if (prefix == null) {
/*  499 */       throw new XMLStreamException("Prefix cannot be null");
/*      */     }
/*      */     
/*  502 */     if (uri == null) {
/*  503 */       throw new XMLStreamException("URI cannot be null");
/*      */     }
/*      */     
/*  506 */     prefix = this.fSymbolTable.addSymbol(prefix);
/*  507 */     uri = this.fSymbolTable.addSymbol(uri);
/*      */     
/*  509 */     if (this.fIsRepairingNamespace) {
/*  510 */       String tmpURI = this.fInternalNamespaceContext.getURI(prefix);
/*      */       
/*  512 */       if (tmpURI != null && tmpURI == uri) {
/*      */         return;
/*      */       }
/*      */       
/*  516 */       if (checkUserNamespaceContext(prefix, uri))
/*      */         return; 
/*  518 */       QName qname = new QName();
/*  519 */       qname.setValues(prefix, "xmlns", null, uri);
/*  520 */       this.fNamespaceDecls.add(qname);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  525 */     this.fInternalNamespaceContext.declarePrefix(prefix, uri);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeAttribute(String localName, String value) throws XMLStreamException {
/*      */     try {
/*  531 */       if (!this.fStartTagOpened) {
/*  532 */         throw new XMLStreamException("Attribute not associated with any element");
/*      */       }
/*      */ 
/*      */       
/*  536 */       if (this.fIsRepairingNamespace) {
/*  537 */         Attribute attr = new Attribute(value);
/*  538 */         attr.setValues(null, localName, null, null);
/*  539 */         this.fAttributeCache.add(attr);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  544 */       this.fWriter.write(" ");
/*  545 */       this.fWriter.write(localName);
/*  546 */       this.fWriter.write("=\"");
/*  547 */       writeXMLContent(value, true, true);
/*      */ 
/*      */ 
/*      */       
/*  551 */       this.fWriter.write("\"");
/*  552 */     } catch (IOException e) {
/*  553 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/*      */     try {
/*  560 */       if (!this.fStartTagOpened) {
/*  561 */         throw new XMLStreamException("Attribute not associated with any element");
/*      */       }
/*      */ 
/*      */       
/*  565 */       if (namespaceURI == null) {
/*  566 */         throw new XMLStreamException("NamespaceURI cannot be null");
/*      */       }
/*      */       
/*  569 */       namespaceURI = this.fSymbolTable.addSymbol(namespaceURI);
/*      */       
/*  571 */       String prefix = this.fInternalNamespaceContext.getPrefix(namespaceURI);
/*      */       
/*  573 */       if (!this.fIsRepairingNamespace) {
/*  574 */         if (prefix == null) {
/*  575 */           throw new XMLStreamException("Prefix cannot be null");
/*      */         }
/*      */         
/*  578 */         writeAttributeWithPrefix(prefix, localName, value);
/*      */       } else {
/*  580 */         Attribute attr = new Attribute(value);
/*  581 */         attr.setValues(null, localName, null, namespaceURI);
/*  582 */         this.fAttributeCache.add(attr);
/*      */       } 
/*  584 */     } catch (IOException e) {
/*  585 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeAttributeWithPrefix(String prefix, String localName, String value) throws IOException {
/*  591 */     this.fWriter.write(" ");
/*      */     
/*  593 */     if (prefix != null && prefix != "") {
/*  594 */       this.fWriter.write(prefix);
/*  595 */       this.fWriter.write(":");
/*      */     } 
/*      */     
/*  598 */     this.fWriter.write(localName);
/*  599 */     this.fWriter.write("=\"");
/*  600 */     writeXMLContent(value, true, true);
/*      */ 
/*      */     
/*  603 */     this.fWriter.write("\"");
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/*      */     try {
/*  609 */       if (!this.fStartTagOpened) {
/*  610 */         throw new XMLStreamException("Attribute not associated with any element");
/*      */       }
/*      */ 
/*      */       
/*  614 */       if (namespaceURI == null) {
/*  615 */         throw new XMLStreamException("NamespaceURI cannot be null");
/*      */       }
/*      */       
/*  618 */       if (localName == null) {
/*  619 */         throw new XMLStreamException("Local name cannot be null");
/*      */       }
/*      */       
/*  622 */       if (!this.fIsRepairingNamespace) {
/*  623 */         if (prefix == null || prefix.equals("")) {
/*  624 */           if (!namespaceURI.equals("")) {
/*  625 */             throw new XMLStreamException("prefix cannot be null or empty");
/*      */           }
/*  627 */           writeAttributeWithPrefix(null, localName, value);
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/*  632 */         if (!prefix.equals("xml") || !namespaceURI.equals("http://www.w3.org/XML/1998/namespace")) {
/*      */           
/*  634 */           prefix = this.fSymbolTable.addSymbol(prefix);
/*  635 */           namespaceURI = this.fSymbolTable.addSymbol(namespaceURI);
/*      */           
/*  637 */           if (this.fInternalNamespaceContext.containsPrefixInCurrentContext(prefix)) {
/*      */             
/*  639 */             String tmpURI = this.fInternalNamespaceContext.getURI(prefix);
/*      */             
/*  641 */             if (tmpURI != null && tmpURI != namespaceURI) {
/*  642 */               throw new XMLStreamException("Prefix " + prefix + " is already bound to " + tmpURI + ". Trying to rebind it to " + namespaceURI + " is an error.");
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/*  647 */           this.fInternalNamespaceContext.declarePrefix(prefix, namespaceURI);
/*      */         } 
/*  649 */         writeAttributeWithPrefix(prefix, localName, value);
/*      */       } else {
/*  651 */         if (prefix != null) {
/*  652 */           prefix = this.fSymbolTable.addSymbol(prefix);
/*      */         }
/*      */         
/*  655 */         namespaceURI = this.fSymbolTable.addSymbol(namespaceURI);
/*      */         
/*  657 */         Attribute attr = new Attribute(value);
/*  658 */         attr.setValues(prefix, localName, null, namespaceURI);
/*  659 */         this.fAttributeCache.add(attr);
/*      */       } 
/*  661 */     } catch (IOException e) {
/*  662 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void writeCData(String cdata) throws XMLStreamException {
/*      */     try {
/*  668 */       if (cdata == null) {
/*  669 */         throw new XMLStreamException("cdata cannot be null");
/*      */       }
/*      */       
/*  672 */       if (this.fStartTagOpened) {
/*  673 */         closeStartTag();
/*      */       }
/*      */       
/*  676 */       this.fWriter.write("<![CDATA[");
/*  677 */       this.fWriter.write(cdata);
/*  678 */       this.fWriter.write("]]>");
/*  679 */     } catch (IOException e) {
/*  680 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void writeCharacters(String data) throws XMLStreamException {
/*      */     try {
/*  686 */       if (this.fStartTagOpened) {
/*  687 */         closeStartTag();
/*      */       }
/*      */       
/*  690 */       writeXMLContent(data);
/*  691 */     } catch (IOException e) {
/*  692 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeCharacters(char[] data, int start, int len) throws XMLStreamException {
/*      */     try {
/*  699 */       if (this.fStartTagOpened) {
/*  700 */         closeStartTag();
/*      */       }
/*      */       
/*  703 */       writeXMLContent(data, start, len, this.fEscapeCharacters);
/*  704 */     } catch (IOException e) {
/*  705 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void writeComment(String comment) throws XMLStreamException {
/*      */     try {
/*  711 */       if (this.fStartTagOpened) {
/*  712 */         closeStartTag();
/*      */       }
/*      */       
/*  715 */       this.fWriter.write("<!--");
/*      */       
/*  717 */       if (comment != null) {
/*  718 */         this.fWriter.write(comment);
/*      */       }
/*      */       
/*  721 */       this.fWriter.write("-->");
/*  722 */     } catch (IOException e) {
/*  723 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void writeDTD(String dtd) throws XMLStreamException {
/*      */     try {
/*  729 */       if (this.fStartTagOpened) {
/*  730 */         closeStartTag();
/*      */       }
/*      */       
/*  733 */       this.fWriter.write(dtd);
/*  734 */     } catch (IOException e) {
/*  735 */       throw new XMLStreamException(e);
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
/*      */   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/*  757 */     String namespaceURINormalized = null;
/*  758 */     if (namespaceURI == null) {
/*  759 */       namespaceURINormalized = "";
/*      */     } else {
/*  761 */       namespaceURINormalized = namespaceURI;
/*      */     } 
/*      */     
/*      */     try {
/*  765 */       if (!this.fStartTagOpened) {
/*  766 */         throw new IllegalStateException("Namespace Attribute not associated with any element");
/*      */       }
/*      */ 
/*      */       
/*  770 */       if (this.fIsRepairingNamespace) {
/*  771 */         QName qname = new QName();
/*  772 */         qname.setValues("", "xmlns", null, namespaceURINormalized);
/*      */         
/*  774 */         this.fNamespaceDecls.add(qname);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  779 */       namespaceURINormalized = this.fSymbolTable.addSymbol(namespaceURINormalized);
/*      */       
/*  781 */       if (this.fInternalNamespaceContext.containsPrefixInCurrentContext("")) {
/*      */         
/*  783 */         String tmp = this.fInternalNamespaceContext.getURI("");
/*      */         
/*  785 */         if (tmp != null && tmp != namespaceURINormalized) {
/*  786 */           throw new XMLStreamException("xmlns has been already bound to " + tmp + ". Rebinding it to " + namespaceURINormalized + " is an error");
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  792 */       this.fInternalNamespaceContext.declarePrefix("", namespaceURINormalized);
/*      */ 
/*      */       
/*  795 */       writenamespace(null, namespaceURINormalized);
/*  796 */     } catch (IOException e) {
/*  797 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void writeEmptyElement(String localName) throws XMLStreamException {
/*      */     try {
/*  803 */       if (this.fStartTagOpened) {
/*  804 */         closeStartTag();
/*      */       }
/*      */       
/*  807 */       openStartTag();
/*  808 */       this.fElementStack.push(null, localName, null, null, true);
/*  809 */       this.fInternalNamespaceContext.pushContext();
/*      */       
/*  811 */       if (!this.fIsRepairingNamespace) {
/*  812 */         this.fWriter.write(localName);
/*      */       }
/*  814 */     } catch (IOException e) {
/*  815 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/*  821 */     if (namespaceURI == null) {
/*  822 */       throw new XMLStreamException("NamespaceURI cannot be null");
/*      */     }
/*      */     
/*  825 */     namespaceURI = this.fSymbolTable.addSymbol(namespaceURI);
/*      */     
/*  827 */     String prefix = this.fNamespaceContext.getPrefix(namespaceURI);
/*  828 */     writeEmptyElement(prefix, localName, namespaceURI);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/*      */     try {
/*  834 */       if (localName == null) {
/*  835 */         throw new XMLStreamException("Local Name cannot be null");
/*      */       }
/*      */       
/*  838 */       if (namespaceURI == null) {
/*  839 */         throw new XMLStreamException("NamespaceURI cannot be null");
/*      */       }
/*      */       
/*  842 */       if (prefix != null) {
/*  843 */         prefix = this.fSymbolTable.addSymbol(prefix);
/*      */       }
/*      */       
/*  846 */       namespaceURI = this.fSymbolTable.addSymbol(namespaceURI);
/*      */       
/*  848 */       if (this.fStartTagOpened) {
/*  849 */         closeStartTag();
/*      */       }
/*      */       
/*  852 */       openStartTag();
/*      */       
/*  854 */       this.fElementStack.push(prefix, localName, null, namespaceURI, true);
/*  855 */       this.fInternalNamespaceContext.pushContext();
/*      */       
/*  857 */       if (!this.fIsRepairingNamespace) {
/*  858 */         if (prefix == null) {
/*  859 */           throw new XMLStreamException("NamespaceURI " + namespaceURI + " has not been bound to any prefix");
/*      */         }
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  866 */       if (prefix != null && prefix != "") {
/*  867 */         this.fWriter.write(prefix);
/*  868 */         this.fWriter.write(":");
/*      */       } 
/*      */       
/*  871 */       this.fWriter.write(localName);
/*  872 */     } catch (IOException e) {
/*  873 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void writeEndDocument() throws XMLStreamException {
/*      */     try {
/*  879 */       if (this.fStartTagOpened) {
/*  880 */         closeStartTag();
/*      */       }
/*      */       
/*  883 */       ElementState elem = null;
/*      */       
/*  885 */       while (!this.fElementStack.empty()) {
/*  886 */         elem = this.fElementStack.pop();
/*  887 */         this.fInternalNamespaceContext.popContext();
/*      */         
/*  889 */         if (elem.isEmpty) {
/*      */           continue;
/*      */         }
/*  892 */         this.fWriter.write("</");
/*      */         
/*  894 */         if (elem.prefix != null && !elem.prefix.equals("")) {
/*  895 */           this.fWriter.write(elem.prefix);
/*  896 */           this.fWriter.write(":");
/*      */         } 
/*      */         
/*  899 */         this.fWriter.write(elem.localpart);
/*  900 */         this.fWriter.write(62);
/*      */       }
/*      */     
/*  903 */     } catch (IOException e) {
/*  904 */       throw new XMLStreamException(e);
/*  905 */     } catch (ArrayIndexOutOfBoundsException e) {
/*  906 */       throw new XMLStreamException("No more elements to write");
/*      */     } 
/*      */   }
/*      */   
/*      */   public void writeEndElement() throws XMLStreamException {
/*      */     try {
/*  912 */       if (this.fStartTagOpened) {
/*  913 */         closeStartTag();
/*      */       }
/*      */       
/*  916 */       ElementState currentElement = this.fElementStack.pop();
/*      */       
/*  918 */       if (currentElement == null) {
/*  919 */         throw new XMLStreamException("No element was found to write");
/*      */       }
/*      */       
/*  922 */       if (currentElement.isEmpty) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  927 */       this.fWriter.write("</");
/*      */       
/*  929 */       if (currentElement.prefix != null && 
/*  930 */         !currentElement.prefix.equals("")) {
/*  931 */         this.fWriter.write(currentElement.prefix);
/*  932 */         this.fWriter.write(":");
/*      */       } 
/*      */       
/*  935 */       this.fWriter.write(currentElement.localpart);
/*  936 */       this.fWriter.write(62);
/*  937 */       this.fInternalNamespaceContext.popContext();
/*  938 */     } catch (IOException e) {
/*  939 */       throw new XMLStreamException(e);
/*  940 */     } catch (ArrayIndexOutOfBoundsException e) {
/*  941 */       throw new XMLStreamException("No element was found to write: " + e
/*      */           
/*  943 */           .toString(), e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void writeEntityRef(String refName) throws XMLStreamException {
/*      */     try {
/*  949 */       if (this.fStartTagOpened) {
/*  950 */         closeStartTag();
/*      */       }
/*      */       
/*  953 */       this.fWriter.write(38);
/*  954 */       this.fWriter.write(refName);
/*  955 */       this.fWriter.write(59);
/*  956 */     } catch (IOException e) {
/*  957 */       throw new XMLStreamException(e);
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
/*      */   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/*  980 */     String namespaceURINormalized = null;
/*  981 */     if (namespaceURI == null) {
/*  982 */       namespaceURINormalized = "";
/*      */     } else {
/*  984 */       namespaceURINormalized = namespaceURI;
/*      */     } 
/*      */     
/*      */     try {
/*  988 */       QName qname = null;
/*      */       
/*  990 */       if (!this.fStartTagOpened) {
/*  991 */         throw new IllegalStateException("Invalid state: start tag is not opened at writeNamespace(" + prefix + ", " + namespaceURINormalized + ")");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1000 */       if (prefix == null || prefix
/* 1001 */         .equals("") || prefix
/* 1002 */         .equals("xmlns")) {
/* 1003 */         writeDefaultNamespace(namespaceURINormalized);
/*      */         
/*      */         return;
/*      */       } 
/* 1007 */       if (prefix.equals("xml") && namespaceURINormalized.equals("http://www.w3.org/XML/1998/namespace")) {
/*      */         return;
/*      */       }
/* 1010 */       prefix = this.fSymbolTable.addSymbol(prefix);
/* 1011 */       namespaceURINormalized = this.fSymbolTable.addSymbol(namespaceURINormalized);
/*      */       
/* 1013 */       if (this.fIsRepairingNamespace) {
/* 1014 */         String tmpURI = this.fInternalNamespaceContext.getURI(prefix);
/*      */         
/* 1016 */         if (tmpURI != null && tmpURI == namespaceURINormalized) {
/*      */           return;
/*      */         }
/*      */         
/* 1020 */         qname = new QName();
/* 1021 */         qname.setValues(prefix, "xmlns", null, namespaceURINormalized);
/*      */         
/* 1023 */         this.fNamespaceDecls.add(qname);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1029 */       if (this.fInternalNamespaceContext.containsPrefixInCurrentContext(prefix)) {
/*      */         
/* 1031 */         String tmp = this.fInternalNamespaceContext.getURI(prefix);
/*      */         
/* 1033 */         if (tmp != null && tmp != namespaceURINormalized)
/*      */         {
/* 1035 */           throw new XMLStreamException("prefix " + prefix + " has been already bound to " + tmp + ". Rebinding it to " + namespaceURINormalized + " is an error");
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1042 */       this.fInternalNamespaceContext.declarePrefix(prefix, namespaceURINormalized);
/* 1043 */       writenamespace(prefix, namespaceURINormalized);
/*      */     }
/* 1045 */     catch (IOException e) {
/* 1046 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writenamespace(String prefix, String namespaceURI) throws IOException {
/* 1052 */     this.fWriter.write(" xmlns");
/*      */     
/* 1054 */     if (prefix != null && prefix != "") {
/* 1055 */       this.fWriter.write(":");
/* 1056 */       this.fWriter.write(prefix);
/*      */     } 
/*      */     
/* 1059 */     this.fWriter.write("=\"");
/* 1060 */     writeXMLContent(namespaceURI, true, true);
/*      */ 
/*      */ 
/*      */     
/* 1064 */     this.fWriter.write("\"");
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/*      */     try {
/* 1070 */       if (this.fStartTagOpened) {
/* 1071 */         closeStartTag();
/*      */       }
/*      */       
/* 1074 */       if (target != null) {
/* 1075 */         this.fWriter.write("<?");
/* 1076 */         this.fWriter.write(target);
/* 1077 */         this.fWriter.write("?>");
/*      */         
/*      */         return;
/*      */       } 
/* 1081 */     } catch (IOException e) {
/* 1082 */       throw new XMLStreamException(e);
/*      */     } 
/*      */     
/* 1085 */     throw new XMLStreamException("PI target cannot be null");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/*      */     try {
/* 1096 */       if (this.fStartTagOpened) {
/* 1097 */         closeStartTag();
/*      */       }
/*      */       
/* 1100 */       if (target == null || data == null) {
/* 1101 */         throw new XMLStreamException("PI target cannot be null");
/*      */       }
/*      */       
/* 1104 */       this.fWriter.write("<?");
/* 1105 */       this.fWriter.write(target);
/* 1106 */       this.fWriter.write(" ");
/* 1107 */       this.fWriter.write(data);
/* 1108 */       this.fWriter.write("?>");
/* 1109 */     } catch (IOException e) {
/* 1110 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartDocument() throws XMLStreamException {
/*      */     try {
/* 1119 */       this.fWriter.write("<?xml version=\"1.0\" ?>");
/* 1120 */     } catch (IOException ex) {
/* 1121 */       throw new XMLStreamException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartDocument(String version) throws XMLStreamException {
/*      */     try {
/* 1131 */       if (version == null || version.equals("")) {
/* 1132 */         writeStartDocument();
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1137 */       this.fWriter.write("<?xml version=\"");
/* 1138 */       this.fWriter.write(version);
/* 1139 */       this.fWriter.write("\"");
/*      */ 
/*      */       
/* 1142 */       this.fWriter.write("?>");
/* 1143 */     } catch (IOException ex) {
/* 1144 */       throw new XMLStreamException(ex);
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
/*      */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/*      */     try {
/* 1157 */       if (encoding == null && version == null) {
/* 1158 */         writeStartDocument();
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1163 */       if (encoding == null) {
/* 1164 */         writeStartDocument(version);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1169 */       String streamEncoding = null;
/* 1170 */       if (this.fWriter instanceof OutputStreamWriter) {
/* 1171 */         streamEncoding = ((OutputStreamWriter)this.fWriter).getEncoding();
/*      */       }
/* 1173 */       else if (this.fWriter instanceof UTF8OutputStreamWriter) {
/* 1174 */         streamEncoding = ((UTF8OutputStreamWriter)this.fWriter).getEncoding();
/*      */       }
/* 1176 */       else if (this.fWriter instanceof XMLWriter) {
/* 1177 */         streamEncoding = ((OutputStreamWriter)((XMLWriter)this.fWriter).getWriter()).getEncoding();
/*      */       } 
/*      */       
/* 1180 */       if (streamEncoding != null && !streamEncoding.equalsIgnoreCase(encoding)) {
/*      */         
/* 1182 */         boolean foundAlias = false;
/* 1183 */         Set<String> aliases = Charset.forName(encoding).aliases();
/* 1184 */         for (Iterator<String> it = aliases.iterator(); !foundAlias && it.hasNext();) {
/* 1185 */           if (streamEncoding.equalsIgnoreCase(it.next())) {
/* 1186 */             foundAlias = true;
/*      */           }
/*      */         } 
/*      */         
/* 1190 */         if (!foundAlias) {
/* 1191 */           throw new XMLStreamException("Underlying stream encoding '" + streamEncoding + "' and input paramter for writeStartDocument() method '" + encoding + "' do not match.");
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1199 */       this.fWriter.write("<?xml version=\"");
/*      */       
/* 1201 */       if (version == null || version.equals("")) {
/* 1202 */         this.fWriter.write("1.0");
/*      */       } else {
/* 1204 */         this.fWriter.write(version);
/*      */       } 
/*      */       
/* 1207 */       if (!encoding.equals("")) {
/* 1208 */         this.fWriter.write("\" encoding=\"");
/* 1209 */         this.fWriter.write(encoding);
/*      */       } 
/*      */       
/* 1212 */       this.fWriter.write("\"?>");
/* 1213 */     } catch (IOException ex) {
/* 1214 */       throw new XMLStreamException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartElement(String localName) throws XMLStreamException {
/*      */     try {
/* 1224 */       if (localName == null) {
/* 1225 */         throw new XMLStreamException("Local Name cannot be null");
/*      */       }
/*      */       
/* 1228 */       if (this.fStartTagOpened) {
/* 1229 */         closeStartTag();
/*      */       }
/*      */       
/* 1232 */       openStartTag();
/* 1233 */       this.fElementStack.push(null, localName, null, null, false);
/* 1234 */       this.fInternalNamespaceContext.pushContext();
/*      */       
/* 1236 */       if (this.fIsRepairingNamespace) {
/*      */         return;
/*      */       }
/*      */       
/* 1240 */       this.fWriter.write(localName);
/* 1241 */     } catch (IOException ex) {
/* 1242 */       throw new XMLStreamException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 1253 */     if (localName == null) {
/* 1254 */       throw new XMLStreamException("Local Name cannot be null");
/*      */     }
/*      */     
/* 1257 */     if (namespaceURI == null) {
/* 1258 */       throw new XMLStreamException("NamespaceURI cannot be null");
/*      */     }
/*      */     
/* 1261 */     namespaceURI = this.fSymbolTable.addSymbol(namespaceURI);
/*      */     
/* 1263 */     String prefix = null;
/*      */     
/* 1265 */     if (!this.fIsRepairingNamespace) {
/* 1266 */       prefix = this.fNamespaceContext.getPrefix(namespaceURI);
/*      */       
/* 1268 */       if (prefix != null) {
/* 1269 */         prefix = this.fSymbolTable.addSymbol(prefix);
/*      */       }
/*      */     } 
/*      */     
/* 1273 */     writeStartElement(prefix, localName, namespaceURI);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/*      */     try {
/* 1285 */       if (localName == null) {
/* 1286 */         throw new XMLStreamException("Local Name cannot be null");
/*      */       }
/*      */       
/* 1289 */       if (namespaceURI == null) {
/* 1290 */         throw new XMLStreamException("NamespaceURI cannot be null");
/*      */       }
/*      */       
/* 1293 */       if (!this.fIsRepairingNamespace && 
/* 1294 */         prefix == null) {
/* 1295 */         throw new XMLStreamException("Prefix cannot be null");
/*      */       }
/*      */ 
/*      */       
/* 1299 */       if (this.fStartTagOpened) {
/* 1300 */         closeStartTag();
/*      */       }
/*      */       
/* 1303 */       openStartTag();
/* 1304 */       namespaceURI = this.fSymbolTable.addSymbol(namespaceURI);
/*      */       
/* 1306 */       if (prefix != null) {
/* 1307 */         prefix = this.fSymbolTable.addSymbol(prefix);
/*      */       }
/*      */       
/* 1310 */       this.fElementStack.push(prefix, localName, null, namespaceURI, false);
/* 1311 */       this.fInternalNamespaceContext.pushContext();
/*      */       
/* 1313 */       String tmpPrefix = this.fNamespaceContext.getPrefix(namespaceURI);
/*      */ 
/*      */       
/* 1316 */       if (prefix != null && (tmpPrefix == null || 
/* 1317 */         !prefix.equals(tmpPrefix))) {
/* 1318 */         this.fInternalNamespaceContext.declarePrefix(prefix, namespaceURI);
/*      */       }
/*      */ 
/*      */       
/* 1322 */       if (this.fIsRepairingNamespace) {
/* 1323 */         if (prefix == null || (tmpPrefix != null && prefix
/* 1324 */           .equals(tmpPrefix))) {
/*      */           return;
/*      */         }
/*      */         
/* 1328 */         QName qname = new QName();
/* 1329 */         qname.setValues(prefix, "xmlns", null, namespaceURI);
/*      */         
/* 1331 */         this.fNamespaceDecls.add(qname);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1336 */       if (prefix != null && prefix != "") {
/* 1337 */         this.fWriter.write(prefix);
/* 1338 */         this.fWriter.write(":");
/*      */       } 
/*      */       
/* 1341 */       this.fWriter.write(localName);
/*      */     }
/* 1343 */     catch (IOException ex) {
/* 1344 */       throw new XMLStreamException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeCharRef(int codePoint) throws IOException {
/* 1352 */     this.fWriter.write("&#x");
/* 1353 */     this.fWriter.write(Integer.toHexString(codePoint));
/* 1354 */     this.fWriter.write(59);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeXMLContent(char[] content, int start, int length, boolean escapeChars) throws IOException {
/* 1363 */     if (!escapeChars) {
/* 1364 */       this.fWriter.write(content, start, length);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1370 */     int startWritePos = start;
/*      */     
/* 1372 */     int end = start + length;
/*      */     
/* 1374 */     for (int index = start; index < end; index++) {
/* 1375 */       char ch = content[index];
/*      */       
/* 1377 */       if (this.fEncoder != null && !this.fEncoder.canEncode(ch)) {
/* 1378 */         this.fWriter.write(content, startWritePos, index - startWritePos);
/*      */ 
/*      */ 
/*      */         
/* 1382 */         if (index != end - 1 && Character.isSurrogatePair(ch, content[index + 1])) {
/* 1383 */           writeCharRef(Character.toCodePoint(ch, content[index + 1]));
/* 1384 */           index++;
/*      */         } else {
/* 1386 */           writeCharRef(ch);
/*      */         } 
/* 1388 */         startWritePos = index + 1;
/*      */       }
/*      */       else {
/*      */         
/* 1392 */         switch (ch) {
/*      */           case '<':
/* 1394 */             this.fWriter.write(content, startWritePos, index - startWritePos);
/* 1395 */             this.fWriter.write("&lt;");
/* 1396 */             startWritePos = index + 1;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '&':
/* 1401 */             this.fWriter.write(content, startWritePos, index - startWritePos);
/* 1402 */             this.fWriter.write("&amp;");
/* 1403 */             startWritePos = index + 1;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '>':
/* 1408 */             this.fWriter.write(content, startWritePos, index - startWritePos);
/* 1409 */             this.fWriter.write("&gt;");
/* 1410 */             startWritePos = index + 1;
/*      */             break;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/*      */     } 
/* 1417 */     this.fWriter.write(content, startWritePos, end - startWritePos);
/*      */   }
/*      */   
/*      */   private void writeXMLContent(String content) throws IOException {
/* 1421 */     if (content != null && content.length() > 0) {
/* 1422 */       writeXMLContent(content, this.fEscapeCharacters, false);
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
/*      */   private void writeXMLContent(String content, boolean escapeChars, boolean escapeDoubleQuotes) throws IOException {
/* 1438 */     if (!escapeChars) {
/* 1439 */       this.fWriter.write(content);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1445 */     int startWritePos = 0;
/*      */     
/* 1447 */     int end = content.length();
/*      */     
/* 1449 */     for (int index = 0; index < end; index++) {
/* 1450 */       char ch = content.charAt(index);
/*      */       
/* 1452 */       if (this.fEncoder != null && !this.fEncoder.canEncode(ch)) {
/* 1453 */         this.fWriter.write(content, startWritePos, index - startWritePos);
/*      */ 
/*      */ 
/*      */         
/* 1457 */         if (index != end - 1 && Character.isSurrogatePair(ch, content.charAt(index + 1))) {
/* 1458 */           writeCharRef(Character.toCodePoint(ch, content.charAt(index + 1)));
/* 1459 */           index++;
/*      */         } else {
/* 1461 */           writeCharRef(ch);
/*      */         } 
/*      */         
/* 1464 */         startWritePos = index + 1;
/*      */       }
/*      */       else {
/*      */         
/* 1468 */         switch (ch) {
/*      */           case '<':
/* 1470 */             this.fWriter.write(content, startWritePos, index - startWritePos);
/* 1471 */             this.fWriter.write("&lt;");
/* 1472 */             startWritePos = index + 1;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '&':
/* 1477 */             this.fWriter.write(content, startWritePos, index - startWritePos);
/* 1478 */             this.fWriter.write("&amp;");
/* 1479 */             startWritePos = index + 1;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '>':
/* 1484 */             this.fWriter.write(content, startWritePos, index - startWritePos);
/* 1485 */             this.fWriter.write("&gt;");
/* 1486 */             startWritePos = index + 1;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '"':
/* 1491 */             this.fWriter.write(content, startWritePos, index - startWritePos);
/* 1492 */             if (escapeDoubleQuotes) {
/* 1493 */               this.fWriter.write("&quot;");
/*      */             } else {
/* 1495 */               this.fWriter.write(34);
/*      */             } 
/* 1497 */             startWritePos = index + 1;
/*      */             break;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/*      */     } 
/* 1504 */     this.fWriter.write(content, startWritePos, end - startWritePos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void closeStartTag() throws XMLStreamException {
/*      */     try {
/* 1512 */       ElementState currentElement = this.fElementStack.peek();
/*      */       
/* 1514 */       if (this.fIsRepairingNamespace) {
/* 1515 */         repair();
/* 1516 */         correctPrefix(currentElement, 1);
/*      */         
/* 1518 */         if (currentElement.prefix != null && currentElement.prefix != "") {
/*      */           
/* 1520 */           this.fWriter.write(currentElement.prefix);
/* 1521 */           this.fWriter.write(":");
/*      */         } 
/*      */         
/* 1524 */         this.fWriter.write(currentElement.localpart);
/*      */         
/* 1526 */         int len = this.fNamespaceDecls.size();
/* 1527 */         QName qname = null;
/*      */         
/* 1529 */         for (int i = 0; i < len; i++) {
/* 1530 */           qname = this.fNamespaceDecls.get(i);
/*      */           
/* 1532 */           if (qname != null && 
/* 1533 */             this.fInternalNamespaceContext.declarePrefix(qname.prefix, qname.uri))
/*      */           {
/* 1535 */             writenamespace(qname.prefix, qname.uri);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1540 */         this.fNamespaceDecls.clear();
/*      */         
/* 1542 */         Attribute attr = null;
/*      */         
/* 1544 */         for (int j = 0; j < this.fAttributeCache.size(); j++) {
/* 1545 */           attr = this.fAttributeCache.get(j);
/*      */           
/* 1547 */           if (attr.prefix != null && attr.uri != null && 
/* 1548 */             !attr.prefix.equals("") && !attr.uri.equals("")) {
/* 1549 */             String tmp = this.fInternalNamespaceContext.getPrefix(attr.uri);
/*      */             
/* 1551 */             if (tmp == null || tmp != attr.prefix) {
/* 1552 */               tmp = getAttrPrefix(attr.uri);
/* 1553 */               if (tmp == null) {
/* 1554 */                 if (this.fInternalNamespaceContext.declarePrefix(attr.prefix, attr.uri))
/*      */                 {
/* 1556 */                   writenamespace(attr.prefix, attr.uri);
/*      */                 }
/*      */               } else {
/* 1559 */                 writenamespace(attr.prefix, attr.uri);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1565 */           writeAttributeWithPrefix(attr.prefix, attr.localpart, attr.value);
/*      */         } 
/*      */         
/* 1568 */         this.fAttrNamespace = null;
/* 1569 */         this.fAttributeCache.clear();
/*      */       } 
/*      */       
/* 1572 */       if (currentElement.isEmpty) {
/* 1573 */         this.fElementStack.pop();
/* 1574 */         this.fInternalNamespaceContext.popContext();
/* 1575 */         this.fWriter.write("/>");
/*      */       } else {
/* 1577 */         this.fWriter.write(62);
/*      */       } 
/*      */       
/* 1580 */       this.fStartTagOpened = false;
/* 1581 */     } catch (IOException ex) {
/* 1582 */       this.fStartTagOpened = false;
/* 1583 */       throw new XMLStreamException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void openStartTag() throws IOException {
/* 1591 */     this.fStartTagOpened = true;
/* 1592 */     this.fWriter.write(60);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void correctPrefix(QName attr, int type) {
/* 1601 */     String tmpPrefix = null;
/*      */ 
/*      */     
/* 1604 */     String prefix = attr.prefix;
/* 1605 */     String uri = attr.uri;
/* 1606 */     boolean isSpecialCaseURI = false;
/*      */     
/* 1608 */     if (prefix == null || prefix.equals("")) {
/* 1609 */       if (uri == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1613 */       if (prefix == "" && uri == "") {
/*      */         return;
/*      */       }
/* 1616 */       uri = this.fSymbolTable.addSymbol(uri);
/*      */       
/* 1618 */       QName decl = null;
/*      */       
/* 1620 */       for (int i = 0; i < this.fNamespaceDecls.size(); i++) {
/* 1621 */         decl = this.fNamespaceDecls.get(i);
/*      */         
/* 1623 */         if (decl != null && decl.uri == attr.uri) {
/* 1624 */           attr.prefix = decl.prefix;
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/* 1630 */       tmpPrefix = this.fNamespaceContext.getPrefix(uri);
/*      */       
/* 1632 */       if (tmpPrefix == "") {
/* 1633 */         if (type == 1) {
/*      */           return;
/*      */         }
/* 1636 */         if (type == 10) {
/*      */           
/* 1638 */           tmpPrefix = getAttrPrefix(uri);
/* 1639 */           isSpecialCaseURI = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1643 */       if (tmpPrefix == null) {
/* 1644 */         StringBuffer genPrefix = new StringBuffer("zdef");
/*      */         
/* 1646 */         for (int j = 0; j < 1; j++) {
/* 1647 */           genPrefix.append(this.fPrefixGen.nextInt());
/*      */         }
/*      */         
/* 1650 */         prefix = genPrefix.toString();
/* 1651 */         prefix = this.fSymbolTable.addSymbol(prefix);
/*      */       } else {
/* 1653 */         prefix = this.fSymbolTable.addSymbol(tmpPrefix);
/*      */       } 
/*      */       
/* 1656 */       if (tmpPrefix == null) {
/* 1657 */         if (isSpecialCaseURI) {
/* 1658 */           addAttrNamespace(prefix, uri);
/*      */         } else {
/* 1660 */           QName qname = new QName();
/* 1661 */           qname.setValues(prefix, "xmlns", null, uri);
/* 1662 */           this.fNamespaceDecls.add(qname);
/* 1663 */           this.fInternalNamespaceContext.declarePrefix(this.fSymbolTable.addSymbol(prefix), uri);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1669 */     attr.prefix = prefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getAttrPrefix(String uri) {
/* 1676 */     if (this.fAttrNamespace != null) {
/* 1677 */       return (String)this.fAttrNamespace.get(uri);
/*      */     }
/* 1679 */     return null;
/*      */   }
/*      */   private void addAttrNamespace(String prefix, String uri) {
/* 1682 */     if (this.fAttrNamespace == null) {
/* 1683 */       this.fAttrNamespace = new HashMap<>();
/*      */     }
/* 1685 */     this.fAttrNamespace.put(prefix, uri);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isDefaultNamespace(String uri) {
/* 1692 */     String defaultNamespace = this.fInternalNamespaceContext.getURI(this.DEFAULT_PREFIX);
/*      */     
/* 1694 */     if (uri == defaultNamespace) {
/* 1695 */       return true;
/*      */     }
/*      */     
/* 1698 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkUserNamespaceContext(String prefix, String uri) {
/* 1707 */     if (this.fNamespaceContext.userContext != null) {
/* 1708 */       String tmpURI = this.fNamespaceContext.userContext.getNamespaceURI(prefix);
/*      */       
/* 1710 */       if (tmpURI != null && tmpURI.equals(uri)) {
/* 1711 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1715 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void repair() {
/* 1722 */     Attribute attr = null;
/* 1723 */     Attribute attr2 = null;
/* 1724 */     ElementState currentElement = this.fElementStack.peek();
/* 1725 */     removeDuplicateDecls();
/*      */     int i;
/* 1727 */     for (i = 0; i < this.fAttributeCache.size(); i++) {
/* 1728 */       attr = this.fAttributeCache.get(i);
/* 1729 */       if ((attr.prefix != null && !attr.prefix.equals("")) || (attr.uri != null && !attr.uri.equals(""))) {
/* 1730 */         correctPrefix(currentElement, attr);
/*      */       }
/*      */     } 
/*      */     
/* 1734 */     if (!isDeclared(currentElement) && 
/* 1735 */       currentElement.prefix != null && currentElement.uri != null)
/*      */     {
/* 1737 */       if (!currentElement.prefix.equals("") && !currentElement.uri.equals("")) {
/* 1738 */         this.fNamespaceDecls.add(currentElement);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 1743 */     for (i = 0; i < this.fAttributeCache.size(); i++) {
/* 1744 */       attr = this.fAttributeCache.get(i);
/* 1745 */       for (int j = i + 1; j < this.fAttributeCache.size(); j++) {
/* 1746 */         attr2 = this.fAttributeCache.get(j);
/* 1747 */         if (!"".equals(attr.prefix) && !"".equals(attr2.prefix)) {
/* 1748 */           correctPrefix(attr, attr2);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1753 */     repairNamespaceDecl(currentElement);
/*      */     
/* 1755 */     i = 0;
/*      */     
/* 1757 */     for (i = 0; i < this.fAttributeCache.size(); i++) {
/* 1758 */       attr = this.fAttributeCache.get(i);
/*      */ 
/*      */ 
/*      */       
/* 1762 */       if (attr.prefix != null && attr.prefix.equals("") && attr.uri != null && attr.uri.equals("")) {
/* 1763 */         repairNamespaceDecl(attr);
/*      */       }
/*      */     } 
/*      */     
/* 1767 */     QName qname = null;
/*      */     
/* 1769 */     for (i = 0; i < this.fNamespaceDecls.size(); i++) {
/* 1770 */       qname = this.fNamespaceDecls.get(i);
/*      */       
/* 1772 */       if (qname != null) {
/* 1773 */         this.fInternalNamespaceContext.declarePrefix(qname.prefix, qname.uri);
/*      */       }
/*      */     } 
/*      */     
/* 1777 */     for (i = 0; i < this.fAttributeCache.size(); i++) {
/* 1778 */       attr = this.fAttributeCache.get(i);
/* 1779 */       correctPrefix(attr, 10);
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
/*      */   void correctPrefix(QName attr1, QName attr2) {
/* 1791 */     String tmpPrefix = null;
/* 1792 */     QName decl = null;
/* 1793 */     boolean done = false;
/*      */     
/* 1795 */     checkForNull(attr1);
/* 1796 */     checkForNull(attr2);
/*      */     
/* 1798 */     if (attr1.prefix.equals(attr2.prefix) && !attr1.uri.equals(attr2.uri)) {
/*      */       
/* 1800 */       tmpPrefix = this.fNamespaceContext.getPrefix(attr2.uri);
/*      */       
/* 1802 */       if (tmpPrefix != null) {
/* 1803 */         attr2.prefix = this.fSymbolTable.addSymbol(tmpPrefix);
/*      */       } else {
/* 1805 */         decl = null;
/* 1806 */         for (int n = 0; n < this.fNamespaceDecls.size(); n++) {
/* 1807 */           decl = this.fNamespaceDecls.get(n);
/* 1808 */           if (decl != null && decl.uri == attr2.uri) {
/* 1809 */             attr2.prefix = decl.prefix;
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         
/* 1816 */         StringBuffer genPrefix = new StringBuffer("zdef");
/*      */         
/* 1818 */         for (int k = 0; k < 1; k++) {
/* 1819 */           genPrefix.append(this.fPrefixGen.nextInt());
/*      */         }
/*      */         
/* 1822 */         tmpPrefix = genPrefix.toString();
/* 1823 */         tmpPrefix = this.fSymbolTable.addSymbol(tmpPrefix);
/* 1824 */         attr2.prefix = tmpPrefix;
/*      */         
/* 1826 */         QName qname = new QName();
/* 1827 */         qname.setValues(tmpPrefix, "xmlns", null, attr2.uri);
/*      */         
/* 1829 */         this.fNamespaceDecls.add(qname);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   void checkForNull(QName attr) {
/* 1835 */     if (attr.prefix == null) attr.prefix = ""; 
/* 1836 */     if (attr.uri == null) attr.uri = "";
/*      */   
/*      */   }
/*      */   
/*      */   void removeDuplicateDecls() {
/* 1841 */     for (int i = 0; i < this.fNamespaceDecls.size(); i++) {
/* 1842 */       QName decl1 = this.fNamespaceDecls.get(i);
/* 1843 */       if (decl1 != null) {
/* 1844 */         for (int j = i + 1; j < this.fNamespaceDecls.size(); j++) {
/* 1845 */           QName decl2 = this.fNamespaceDecls.get(j);
/*      */ 
/*      */           
/* 1848 */           if (decl2 != null && decl1.prefix.equals(decl2.prefix) && decl1.uri.equals(decl2.uri)) {
/* 1849 */             this.fNamespaceDecls.remove(j);
/*      */           }
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
/*      */   void repairNamespaceDecl(QName attr) {
/* 1863 */     QName decl = null;
/*      */ 
/*      */ 
/*      */     
/* 1867 */     for (int j = 0; j < this.fNamespaceDecls.size(); j++) {
/* 1868 */       decl = this.fNamespaceDecls.get(j);
/*      */       
/* 1870 */       if (decl != null && 
/* 1871 */         attr.prefix != null && attr.prefix
/* 1872 */         .equals(decl.prefix) && 
/* 1873 */         !attr.uri.equals(decl.uri)) {
/* 1874 */         String tmpURI = this.fNamespaceContext.getNamespaceURI(attr.prefix);
/*      */ 
/*      */         
/* 1877 */         if (tmpURI != null) {
/* 1878 */           if (tmpURI.equals(attr.uri)) {
/* 1879 */             this.fNamespaceDecls.set(j, null);
/*      */           } else {
/* 1881 */             decl.uri = attr.uri;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean isDeclared(QName attr) {
/* 1890 */     QName decl = null;
/*      */     
/* 1892 */     for (int n = 0; n < this.fNamespaceDecls.size(); n++) {
/* 1893 */       decl = this.fNamespaceDecls.get(n);
/*      */       
/* 1895 */       if (attr.prefix != null && attr.prefix == decl.prefix && decl.uri == attr.uri)
/*      */       {
/* 1897 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1901 */     if (attr.uri != null && 
/* 1902 */       this.fNamespaceContext.getPrefix(attr.uri) != null) {
/* 1903 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1907 */     return false;
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
/*      */   protected class ElementStack
/*      */   {
/* 1923 */     protected XMLStreamWriterImpl.ElementState[] fElements = new XMLStreamWriterImpl.ElementState[10];
/*      */     public ElementStack() {
/* 1925 */       for (int i = 0; i < this.fElements.length; i++) {
/* 1926 */         this.fElements[i] = new XMLStreamWriterImpl.ElementState();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected short fDepth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public XMLStreamWriterImpl.ElementState push(XMLStreamWriterImpl.ElementState element) {
/* 1944 */       if (this.fDepth == this.fElements.length) {
/* 1945 */         XMLStreamWriterImpl.ElementState[] array = new XMLStreamWriterImpl.ElementState[this.fElements.length * 2];
/* 1946 */         System.arraycopy(this.fElements, 0, array, 0, this.fDepth);
/* 1947 */         this.fElements = array;
/*      */         
/* 1949 */         for (int i = this.fDepth; i < this.fElements.length; i++) {
/* 1950 */           this.fElements[i] = new XMLStreamWriterImpl.ElementState();
/*      */         }
/*      */       } 
/*      */       
/* 1954 */       this.fElements[this.fDepth].setValues(element);
/*      */       
/* 1956 */       this.fDepth = (short)(this.fDepth + 1); return this.fElements[this.fDepth];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public XMLStreamWriterImpl.ElementState push(String prefix, String localpart, String rawname, String uri, boolean isEmpty) {
/* 1970 */       if (this.fDepth == this.fElements.length) {
/* 1971 */         XMLStreamWriterImpl.ElementState[] array = new XMLStreamWriterImpl.ElementState[this.fElements.length * 2];
/* 1972 */         System.arraycopy(this.fElements, 0, array, 0, this.fDepth);
/* 1973 */         this.fElements = array;
/*      */         
/* 1975 */         for (int i = this.fDepth; i < this.fElements.length; i++) {
/* 1976 */           this.fElements[i] = new XMLStreamWriterImpl.ElementState();
/*      */         }
/*      */       } 
/*      */       
/* 1980 */       this.fElements[this.fDepth].setValues(prefix, localpart, rawname, uri, isEmpty);
/*      */       
/* 1982 */       this.fDepth = (short)(this.fDepth + 1); return this.fElements[this.fDepth];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public XMLStreamWriterImpl.ElementState pop() {
/* 1994 */       return this.fElements[this.fDepth = (short)(this.fDepth - 1)];
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1999 */       this.fDepth = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public XMLStreamWriterImpl.ElementState peek() {
/* 2010 */       return this.fElements[this.fDepth - 1];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean empty() {
/* 2018 */       return !(this.fDepth > 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class ElementState
/*      */     extends QName
/*      */   {
/*      */     public boolean isEmpty = false;
/*      */ 
/*      */     
/*      */     public ElementState() {}
/*      */     
/*      */     public ElementState(String prefix, String localpart, String rawname, String uri) {
/* 2032 */       super(prefix, localpart, rawname, uri);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValues(String prefix, String localpart, String rawname, String uri, boolean isEmpty) {
/* 2037 */       setValues(prefix, localpart, rawname, uri);
/* 2038 */       this.isEmpty = isEmpty;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class Attribute
/*      */     extends QName
/*      */   {
/*      */     String value;
/*      */ 
/*      */     
/*      */     Attribute(String value) {
/* 2050 */       this.value = value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class NamespaceContextImpl
/*      */     implements NamespaceContext
/*      */   {
/* 2060 */     NamespaceContext userContext = null;
/*      */ 
/*      */     
/* 2063 */     NamespaceSupport internalContext = null;
/*      */     
/*      */     public String getNamespaceURI(String prefix) {
/* 2066 */       String uri = null;
/*      */       
/* 2068 */       if (prefix != null) {
/* 2069 */         prefix = XMLStreamWriterImpl.this.fSymbolTable.addSymbol(prefix);
/*      */       }
/*      */       
/* 2072 */       if (this.internalContext != null) {
/* 2073 */         uri = this.internalContext.getURI(prefix);
/*      */         
/* 2075 */         if (uri != null) {
/* 2076 */           return uri;
/*      */         }
/*      */       } 
/*      */       
/* 2080 */       if (this.userContext != null) {
/* 2081 */         uri = this.userContext.getNamespaceURI(prefix);
/*      */         
/* 2083 */         return uri;
/*      */       } 
/*      */       
/* 2086 */       return null;
/*      */     }
/*      */     
/*      */     public String getPrefix(String uri) {
/* 2090 */       String prefix = null;
/*      */       
/* 2092 */       if (uri != null) {
/* 2093 */         uri = XMLStreamWriterImpl.this.fSymbolTable.addSymbol(uri);
/*      */       }
/*      */       
/* 2096 */       if (this.internalContext != null) {
/* 2097 */         prefix = this.internalContext.getPrefix(uri);
/*      */         
/* 2099 */         if (prefix != null) {
/* 2100 */           return prefix;
/*      */         }
/*      */       } 
/*      */       
/* 2104 */       if (this.userContext != null) {
/* 2105 */         return this.userContext.getPrefix(uri);
/*      */       }
/*      */       
/* 2108 */       return null;
/*      */     }
/*      */     
/*      */     public Iterator getPrefixes(String uri) {
/* 2112 */       Vector<?> prefixes = null;
/* 2113 */       Iterator<String> itr = null;
/*      */       
/* 2115 */       if (uri != null) {
/* 2116 */         uri = XMLStreamWriterImpl.this.fSymbolTable.addSymbol(uri);
/*      */       }
/*      */       
/* 2119 */       if (this.userContext != null) {
/* 2120 */         itr = this.userContext.getPrefixes(uri);
/*      */       }
/*      */       
/* 2123 */       if (this.internalContext != null) {
/* 2124 */         prefixes = this.internalContext.getPrefixes(uri);
/*      */       }
/*      */       
/* 2127 */       if (prefixes == null && itr != null)
/* 2128 */         return itr; 
/* 2129 */       if (prefixes != null && itr == null)
/* 2130 */         return new ReadOnlyIterator(prefixes.iterator()); 
/* 2131 */       if (prefixes != null && itr != null) {
/* 2132 */         String ob = null;
/*      */         
/* 2134 */         while (itr.hasNext()) {
/* 2135 */           ob = itr.next();
/*      */           
/* 2137 */           if (ob != null) {
/* 2138 */             ob = XMLStreamWriterImpl.this.fSymbolTable.addSymbol(ob);
/*      */           }
/*      */           
/* 2141 */           if (!prefixes.contains(ob)) {
/* 2142 */             prefixes.add(ob);
/*      */           }
/*      */         } 
/*      */         
/* 2146 */         return new ReadOnlyIterator(prefixes.iterator());
/*      */       } 
/*      */       
/* 2149 */       return XMLStreamWriterImpl.this.fReadOnlyIterator;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/* 2156 */     return 1;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 2160 */     return false;
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object key) {
/* 2164 */     return key.equals("sjsxp-outputstream");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(Object key) {
/* 2172 */     if (key.equals("sjsxp-outputstream")) {
/* 2173 */       return this.fOutputStream;
/*      */     }
/* 2175 */     return null;
/*      */   }
/*      */   
/*      */   public Set entrySet() {
/* 2179 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2189 */     return getClass().getName() + "@" + Integer.toHexString(hashCode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2197 */     return this.fElementStack.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 2204 */     return (this == obj);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/writers/XMLStreamWriterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */