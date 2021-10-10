/*      */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.StripFilter;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.TransletException;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMAxisTraverser;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*      */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLString;
/*      */ import java.util.Map;
/*      */ import javax.xml.transform.SourceLocator;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.ext.DeclHandler;
/*      */ import org.xml.sax.ext.LexicalHandler;
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
/*      */ public class AdaptiveResultTreeImpl
/*      */   extends SimpleResultTreeImpl
/*      */ {
/*   75 */   private static int _documentURIIndex = 0;
/*      */   
/*   77 */   private static final String EMPTY_STRING = "".intern();
/*      */ 
/*      */ 
/*      */   
/*      */   private SAXImpl _dom;
/*      */ 
/*      */ 
/*      */   
/*      */   private DTMWSFilter _wsfilter;
/*      */ 
/*      */   
/*      */   private int _initSize;
/*      */ 
/*      */   
/*      */   private boolean _buildIdIndex;
/*      */ 
/*      */   
/*   94 */   private final AttributesImpl _attributes = new AttributesImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String _openElementName;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AdaptiveResultTreeImpl(XSLTCDTMManager dtmManager, int documentID, DTMWSFilter wsfilter, int initSize, boolean buildIdIndex) {
/*  105 */     super(dtmManager, documentID);
/*      */     
/*  107 */     this._wsfilter = wsfilter;
/*  108 */     this._initSize = initSize;
/*  109 */     this._buildIdIndex = buildIdIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DOM getNestedDOM() {
/*  115 */     return this._dom;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDocument() {
/*  121 */     if (this._dom != null) {
/*  122 */       return this._dom.getDocument();
/*      */     }
/*      */     
/*  125 */     return super.getDocument();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStringValue() {
/*  132 */     if (this._dom != null) {
/*  133 */       return this._dom.getStringValue();
/*      */     }
/*      */     
/*  136 */     return super.getStringValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getIterator() {
/*  142 */     if (this._dom != null) {
/*  143 */       return this._dom.getIterator();
/*      */     }
/*      */     
/*  146 */     return super.getIterator();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getChildren(int node) {
/*  152 */     if (this._dom != null) {
/*  153 */       return this._dom.getChildren(node);
/*      */     }
/*      */     
/*  156 */     return super.getChildren(node);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getTypedChildren(int type) {
/*  162 */     if (this._dom != null) {
/*  163 */       return this._dom.getTypedChildren(type);
/*      */     }
/*      */     
/*  166 */     return super.getTypedChildren(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getAxisIterator(int axis) {
/*  172 */     if (this._dom != null) {
/*  173 */       return this._dom.getAxisIterator(axis);
/*      */     }
/*      */     
/*  176 */     return super.getAxisIterator(axis);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getTypedAxisIterator(int axis, int type) {
/*  182 */     if (this._dom != null) {
/*  183 */       return this._dom.getTypedAxisIterator(axis, type);
/*      */     }
/*      */     
/*  186 */     return super.getTypedAxisIterator(axis, type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getNthDescendant(int node, int n, boolean includeself) {
/*  192 */     if (this._dom != null) {
/*  193 */       return this._dom.getNthDescendant(node, n, includeself);
/*      */     }
/*      */     
/*  196 */     return super.getNthDescendant(node, n, includeself);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns) {
/*  202 */     if (this._dom != null) {
/*  203 */       return this._dom.getNamespaceAxisIterator(axis, ns);
/*      */     }
/*      */     
/*  206 */     return super.getNamespaceAxisIterator(axis, ns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iter, int returnType, String value, boolean op) {
/*  213 */     if (this._dom != null) {
/*  214 */       return this._dom.getNodeValueIterator(iter, returnType, value, op);
/*      */     }
/*      */     
/*  217 */     return super.getNodeValueIterator(iter, returnType, value, op);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node) {
/*  223 */     if (this._dom != null) {
/*  224 */       return this._dom.orderNodes(source, node);
/*      */     }
/*      */     
/*  227 */     return super.orderNodes(source, node);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNodeName(int node) {
/*  233 */     if (this._dom != null) {
/*  234 */       return this._dom.getNodeName(node);
/*      */     }
/*      */     
/*  237 */     return super.getNodeName(node);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNodeNameX(int node) {
/*  243 */     if (this._dom != null) {
/*  244 */       return this._dom.getNodeNameX(node);
/*      */     }
/*      */     
/*  247 */     return super.getNodeNameX(node);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespaceName(int node) {
/*  253 */     if (this._dom != null) {
/*  254 */       return this._dom.getNamespaceName(node);
/*      */     }
/*      */     
/*  257 */     return super.getNamespaceName(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getExpandedTypeID(int nodeHandle) {
/*  264 */     if (this._dom != null) {
/*  265 */       return this._dom.getExpandedTypeID(nodeHandle);
/*      */     }
/*      */     
/*  268 */     return super.getExpandedTypeID(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNamespaceType(int node) {
/*  274 */     if (this._dom != null) {
/*  275 */       return this._dom.getNamespaceType(node);
/*      */     }
/*      */     
/*  278 */     return super.getNamespaceType(node);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getParent(int nodeHandle) {
/*  284 */     if (this._dom != null) {
/*  285 */       return this._dom.getParent(nodeHandle);
/*      */     }
/*      */     
/*  288 */     return super.getParent(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAttributeNode(int gType, int element) {
/*  294 */     if (this._dom != null) {
/*  295 */       return this._dom.getAttributeNode(gType, element);
/*      */     }
/*      */     
/*  298 */     return super.getAttributeNode(gType, element);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStringValueX(int nodeHandle) {
/*  304 */     if (this._dom != null) {
/*  305 */       return this._dom.getStringValueX(nodeHandle);
/*      */     }
/*      */     
/*  308 */     return super.getStringValueX(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copy(int node, SerializationHandler handler) throws TransletException {
/*  315 */     if (this._dom != null) {
/*  316 */       this._dom.copy(node, handler);
/*      */     } else {
/*      */       
/*  319 */       super.copy(node, handler);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void copy(DTMAxisIterator nodes, SerializationHandler handler) throws TransletException {
/*  326 */     if (this._dom != null) {
/*  327 */       this._dom.copy(nodes, handler);
/*      */     } else {
/*      */       
/*  330 */       super.copy(nodes, handler);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String shallowCopy(int node, SerializationHandler handler) throws TransletException {
/*  337 */     if (this._dom != null) {
/*  338 */       return this._dom.shallowCopy(node, handler);
/*      */     }
/*      */     
/*  341 */     return super.shallowCopy(node, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean lessThan(int node1, int node2) {
/*  347 */     if (this._dom != null) {
/*  348 */       return this._dom.lessThan(node1, node2);
/*      */     }
/*      */     
/*  351 */     return super.lessThan(node1, node2);
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
/*      */   public void characters(int node, SerializationHandler handler) throws TransletException {
/*  364 */     if (this._dom != null) {
/*  365 */       this._dom.characters(node, handler);
/*      */     } else {
/*      */       
/*  368 */       super.characters(node, handler);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Node makeNode(int index) {
/*  374 */     if (this._dom != null) {
/*  375 */       return this._dom.makeNode(index);
/*      */     }
/*      */     
/*  378 */     return super.makeNode(index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Node makeNode(DTMAxisIterator iter) {
/*  384 */     if (this._dom != null) {
/*  385 */       return this._dom.makeNode(iter);
/*      */     }
/*      */     
/*  388 */     return super.makeNode(iter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public NodeList makeNodeList(int index) {
/*  394 */     if (this._dom != null) {
/*  395 */       return this._dom.makeNodeList(index);
/*      */     }
/*      */     
/*  398 */     return super.makeNodeList(index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public NodeList makeNodeList(DTMAxisIterator iter) {
/*  404 */     if (this._dom != null) {
/*  405 */       return this._dom.makeNodeList(iter);
/*      */     }
/*      */     
/*  408 */     return super.makeNodeList(iter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLanguage(int node) {
/*  414 */     if (this._dom != null) {
/*  415 */       return this._dom.getLanguage(node);
/*      */     }
/*      */     
/*  418 */     return super.getLanguage(node);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSize() {
/*  424 */     if (this._dom != null) {
/*  425 */       return this._dom.getSize();
/*      */     }
/*      */     
/*  428 */     return super.getSize();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDocumentURI(int node) {
/*  434 */     if (this._dom != null) {
/*  435 */       return this._dom.getDocumentURI(node);
/*      */     }
/*      */     
/*  438 */     return "adaptive_rtf" + _documentURIIndex++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFilter(StripFilter filter) {
/*  444 */     if (this._dom != null) {
/*  445 */       this._dom.setFilter(filter);
/*      */     } else {
/*      */       
/*  448 */       super.setFilter(filter);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setupMapping(String[] names, String[] uris, int[] types, String[] namespaces) {
/*  454 */     if (this._dom != null) {
/*  455 */       this._dom.setupMapping(names, uris, types, namespaces);
/*      */     } else {
/*      */       
/*  458 */       super.setupMapping(names, uris, types, namespaces);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isElement(int node) {
/*  464 */     if (this._dom != null) {
/*  465 */       return this._dom.isElement(node);
/*      */     }
/*      */     
/*  468 */     return super.isElement(node);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttribute(int node) {
/*  474 */     if (this._dom != null) {
/*  475 */       return this._dom.isAttribute(node);
/*      */     }
/*      */     
/*  478 */     return super.isAttribute(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String lookupNamespace(int node, String prefix) throws TransletException {
/*  485 */     if (this._dom != null) {
/*  486 */       return this._dom.lookupNamespace(node, prefix);
/*      */     }
/*      */     
/*  489 */     return super.lookupNamespace(node, prefix);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNodeIdent(int nodehandle) {
/*  498 */     if (this._dom != null) {
/*  499 */       return this._dom.getNodeIdent(nodehandle);
/*      */     }
/*      */     
/*  502 */     return super.getNodeIdent(nodehandle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNodeHandle(int nodeId) {
/*  511 */     if (this._dom != null) {
/*  512 */       return this._dom.getNodeHandle(nodeId);
/*      */     }
/*      */     
/*  515 */     return super.getNodeHandle(nodeId);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DOM getResultTreeFrag(int initialSize, int rtfType) {
/*  521 */     if (this._dom != null) {
/*  522 */       return this._dom.getResultTreeFrag(initialSize, rtfType);
/*      */     }
/*      */     
/*  525 */     return super.getResultTreeFrag(initialSize, rtfType);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SerializationHandler getOutputDomBuilder() {
/*  531 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNSType(int node) {
/*  536 */     if (this._dom != null) {
/*  537 */       return this._dom.getNSType(node);
/*      */     }
/*      */     
/*  540 */     return super.getNSType(node);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnparsedEntityURI(String name) {
/*  546 */     if (this._dom != null) {
/*  547 */       return this._dom.getUnparsedEntityURI(name);
/*      */     }
/*      */     
/*  550 */     return super.getUnparsedEntityURI(name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Integer> getElementsWithIDs() {
/*  556 */     if (this._dom != null) {
/*  557 */       return this._dom.getElementsWithIDs();
/*      */     }
/*      */     
/*  560 */     return super.getElementsWithIDs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void maybeEmitStartElement() throws SAXException {
/*  570 */     if (this._openElementName != null) {
/*      */       int index;
/*      */       
/*  573 */       if ((index = this._openElementName.indexOf(':')) < 0) {
/*  574 */         this._dom.startElement((String)null, this._openElementName, this._openElementName, this._attributes);
/*      */       } else {
/*  576 */         String uri = this._dom.getNamespaceURI(this._openElementName.substring(0, index));
/*  577 */         this._dom.startElement(uri, this._openElementName.substring(index + 1), this._openElementName, this._attributes);
/*      */       } 
/*      */ 
/*      */       
/*  581 */       this._openElementName = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void prepareNewDOM() throws SAXException {
/*  589 */     this._dom = (SAXImpl)this._dtmManager.getDTM(null, true, this._wsfilter, true, false, false, this._initSize, this._buildIdIndex);
/*      */ 
/*      */     
/*  592 */     this._dom.startDocument();
/*      */     
/*  594 */     for (int i = 0; i < this._size; i++) {
/*  595 */       String str = this._textArray[i];
/*  596 */       this._dom.characters(str.toCharArray(), 0, str.length());
/*      */     } 
/*  598 */     this._size = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void startDocument() throws SAXException {}
/*      */ 
/*      */   
/*      */   public void endDocument() throws SAXException {
/*  607 */     if (this._dom != null) {
/*  608 */       this._dom.endDocument();
/*      */     } else {
/*      */       
/*  611 */       super.endDocument();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void characters(String str) throws SAXException {
/*  617 */     if (this._dom != null) {
/*  618 */       characters(str.toCharArray(), 0, str.length());
/*      */     } else {
/*      */       
/*  621 */       super.characters(str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void characters(char[] ch, int offset, int length) throws SAXException {
/*  628 */     if (this._dom != null) {
/*  629 */       maybeEmitStartElement();
/*  630 */       this._dom.characters(ch, offset, length);
/*      */     } else {
/*      */       
/*  633 */       super.characters(ch, offset, length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setEscaping(boolean escape) throws SAXException {
/*  639 */     if (this._dom != null) {
/*  640 */       return this._dom.setEscaping(escape);
/*      */     }
/*      */     
/*  643 */     return super.setEscaping(escape);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElement(String elementName) throws SAXException {
/*  649 */     if (this._dom == null) {
/*  650 */       prepareNewDOM();
/*      */     }
/*      */     
/*  653 */     maybeEmitStartElement();
/*  654 */     this._openElementName = elementName;
/*  655 */     this._attributes.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElement(String uri, String localName, String qName) throws SAXException {
/*  661 */     startElement(qName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/*  667 */     startElement(qName);
/*      */   }
/*      */ 
/*      */   
/*      */   public void endElement(String elementName) throws SAXException {
/*  672 */     maybeEmitStartElement();
/*  673 */     this._dom.endElement((String)null, (String)null, elementName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement(String uri, String localName, String qName) throws SAXException {
/*  679 */     endElement(qName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAttribute(String qName, String value) {
/*  685 */     int colonpos = qName.indexOf(':');
/*  686 */     String uri = EMPTY_STRING;
/*  687 */     String localName = qName;
/*  688 */     if (colonpos > 0) {
/*      */       
/*  690 */       String prefix = qName.substring(0, colonpos);
/*  691 */       localName = qName.substring(colonpos + 1);
/*  692 */       uri = this._dom.getNamespaceURI(prefix);
/*      */     } 
/*      */     
/*  695 */     addAttribute(uri, localName, qName, "CDATA", value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addUniqueAttribute(String qName, String value, int flags) throws SAXException {
/*  701 */     addAttribute(qName, value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAttribute(String uri, String localName, String qname, String type, String value) {
/*  707 */     if (this._openElementName != null) {
/*  708 */       this._attributes.addAttribute(uri, localName, qname, type, value);
/*      */     } else {
/*      */       
/*  711 */       BasisLibrary.runTimeError("STRAY_ATTRIBUTE_ERR", qname);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void namespaceAfterStartElement(String prefix, String uri) throws SAXException {
/*  718 */     if (this._dom == null) {
/*  719 */       prepareNewDOM();
/*      */     }
/*      */     
/*  722 */     this._dom.startPrefixMapping(prefix, uri);
/*      */   }
/*      */ 
/*      */   
/*      */   public void comment(String comment) throws SAXException {
/*  727 */     if (this._dom == null) {
/*  728 */       prepareNewDOM();
/*      */     }
/*      */     
/*  731 */     maybeEmitStartElement();
/*  732 */     char[] chars = comment.toCharArray();
/*  733 */     this._dom.comment(chars, 0, chars.length);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void comment(char[] chars, int offset, int length) throws SAXException {
/*  739 */     if (this._dom == null) {
/*  740 */       prepareNewDOM();
/*      */     }
/*      */     
/*  743 */     maybeEmitStartElement();
/*  744 */     this._dom.comment(chars, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void processingInstruction(String target, String data) throws SAXException {
/*  750 */     if (this._dom == null) {
/*  751 */       prepareNewDOM();
/*      */     }
/*      */     
/*  754 */     maybeEmitStartElement();
/*  755 */     this._dom.processingInstruction(target, data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String featureId, boolean state) {
/*  762 */     if (this._dom != null) {
/*  763 */       this._dom.setFeature(featureId, state);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProperty(String property, Object value) {
/*  769 */     if (this._dom != null) {
/*  770 */       this._dom.setProperty(property, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DTMAxisTraverser getAxisTraverser(int axis) {
/*  776 */     if (this._dom != null) {
/*  777 */       return this._dom.getAxisTraverser(axis);
/*      */     }
/*      */     
/*  780 */     return super.getAxisTraverser(axis);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasChildNodes(int nodeHandle) {
/*  786 */     if (this._dom != null) {
/*  787 */       return this._dom.hasChildNodes(nodeHandle);
/*      */     }
/*      */     
/*  790 */     return super.hasChildNodes(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFirstChild(int nodeHandle) {
/*  796 */     if (this._dom != null) {
/*  797 */       return this._dom.getFirstChild(nodeHandle);
/*      */     }
/*      */     
/*  800 */     return super.getFirstChild(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLastChild(int nodeHandle) {
/*  806 */     if (this._dom != null) {
/*  807 */       return this._dom.getLastChild(nodeHandle);
/*      */     }
/*      */     
/*  810 */     return super.getLastChild(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAttributeNode(int elementHandle, String namespaceURI, String name) {
/*  816 */     if (this._dom != null) {
/*  817 */       return this._dom.getAttributeNode(elementHandle, namespaceURI, name);
/*      */     }
/*      */     
/*  820 */     return super.getAttributeNode(elementHandle, namespaceURI, name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFirstAttribute(int nodeHandle) {
/*  826 */     if (this._dom != null) {
/*  827 */       return this._dom.getFirstAttribute(nodeHandle);
/*      */     }
/*      */     
/*  830 */     return super.getFirstAttribute(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFirstNamespaceNode(int nodeHandle, boolean inScope) {
/*  836 */     if (this._dom != null) {
/*  837 */       return this._dom.getFirstNamespaceNode(nodeHandle, inScope);
/*      */     }
/*      */     
/*  840 */     return super.getFirstNamespaceNode(nodeHandle, inScope);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextSibling(int nodeHandle) {
/*  846 */     if (this._dom != null) {
/*  847 */       return this._dom.getNextSibling(nodeHandle);
/*      */     }
/*      */     
/*  850 */     return super.getNextSibling(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPreviousSibling(int nodeHandle) {
/*  856 */     if (this._dom != null) {
/*  857 */       return this._dom.getPreviousSibling(nodeHandle);
/*      */     }
/*      */     
/*  860 */     return super.getPreviousSibling(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextAttribute(int nodeHandle) {
/*  866 */     if (this._dom != null) {
/*  867 */       return this._dom.getNextAttribute(nodeHandle);
/*      */     }
/*      */     
/*  870 */     return super.getNextAttribute(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextNamespaceNode(int baseHandle, int namespaceHandle, boolean inScope) {
/*  877 */     if (this._dom != null) {
/*  878 */       return this._dom.getNextNamespaceNode(baseHandle, namespaceHandle, inScope);
/*      */     }
/*      */     
/*  881 */     return super.getNextNamespaceNode(baseHandle, namespaceHandle, inScope);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOwnerDocument(int nodeHandle) {
/*  887 */     if (this._dom != null) {
/*  888 */       return this._dom.getOwnerDocument(nodeHandle);
/*      */     }
/*      */     
/*  891 */     return super.getOwnerDocument(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDocumentRoot(int nodeHandle) {
/*  897 */     if (this._dom != null) {
/*  898 */       return this._dom.getDocumentRoot(nodeHandle);
/*      */     }
/*      */     
/*  901 */     return super.getDocumentRoot(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLString getStringValue(int nodeHandle) {
/*  907 */     if (this._dom != null) {
/*  908 */       return this._dom.getStringValue(nodeHandle);
/*      */     }
/*      */     
/*  911 */     return super.getStringValue(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStringValueChunkCount(int nodeHandle) {
/*  917 */     if (this._dom != null) {
/*  918 */       return this._dom.getStringValueChunkCount(nodeHandle);
/*      */     }
/*      */     
/*  921 */     return super.getStringValueChunkCount(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] getStringValueChunk(int nodeHandle, int chunkIndex, int[] startAndLen) {
/*  928 */     if (this._dom != null) {
/*  929 */       return this._dom.getStringValueChunk(nodeHandle, chunkIndex, startAndLen);
/*      */     }
/*      */     
/*  932 */     return super.getStringValueChunk(nodeHandle, chunkIndex, startAndLen);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getExpandedTypeID(String namespace, String localName, int type) {
/*  938 */     if (this._dom != null) {
/*  939 */       return this._dom.getExpandedTypeID(namespace, localName, type);
/*      */     }
/*      */     
/*  942 */     return super.getExpandedTypeID(namespace, localName, type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalNameFromExpandedNameID(int ExpandedNameID) {
/*  948 */     if (this._dom != null) {
/*  949 */       return this._dom.getLocalNameFromExpandedNameID(ExpandedNameID);
/*      */     }
/*      */     
/*  952 */     return super.getLocalNameFromExpandedNameID(ExpandedNameID);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespaceFromExpandedNameID(int ExpandedNameID) {
/*  958 */     if (this._dom != null) {
/*  959 */       return this._dom.getNamespaceFromExpandedNameID(ExpandedNameID);
/*      */     }
/*      */     
/*  962 */     return super.getNamespaceFromExpandedNameID(ExpandedNameID);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalName(int nodeHandle) {
/*  968 */     if (this._dom != null) {
/*  969 */       return this._dom.getLocalName(nodeHandle);
/*      */     }
/*      */     
/*  972 */     return super.getLocalName(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPrefix(int nodeHandle) {
/*  978 */     if (this._dom != null) {
/*  979 */       return this._dom.getPrefix(nodeHandle);
/*      */     }
/*      */     
/*  982 */     return super.getPrefix(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespaceURI(int nodeHandle) {
/*  988 */     if (this._dom != null) {
/*  989 */       return this._dom.getNamespaceURI(nodeHandle);
/*      */     }
/*      */     
/*  992 */     return super.getNamespaceURI(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNodeValue(int nodeHandle) {
/*  998 */     if (this._dom != null) {
/*  999 */       return this._dom.getNodeValue(nodeHandle);
/*      */     }
/*      */     
/* 1002 */     return super.getNodeValue(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getNodeType(int nodeHandle) {
/* 1008 */     if (this._dom != null) {
/* 1009 */       return this._dom.getNodeType(nodeHandle);
/*      */     }
/*      */     
/* 1012 */     return super.getNodeType(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getLevel(int nodeHandle) {
/* 1018 */     if (this._dom != null) {
/* 1019 */       return this._dom.getLevel(nodeHandle);
/*      */     }
/*      */     
/* 1022 */     return super.getLevel(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSupported(String feature, String version) {
/* 1028 */     if (this._dom != null) {
/* 1029 */       return this._dom.isSupported(feature, version);
/*      */     }
/*      */     
/* 1032 */     return super.isSupported(feature, version);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDocumentBaseURI() {
/* 1038 */     if (this._dom != null) {
/* 1039 */       return this._dom.getDocumentBaseURI();
/*      */     }
/*      */     
/* 1042 */     return super.getDocumentBaseURI();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentBaseURI(String baseURI) {
/* 1048 */     if (this._dom != null) {
/* 1049 */       this._dom.setDocumentBaseURI(baseURI);
/*      */     } else {
/*      */       
/* 1052 */       super.setDocumentBaseURI(baseURI);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDocumentSystemIdentifier(int nodeHandle) {
/* 1058 */     if (this._dom != null) {
/* 1059 */       return this._dom.getDocumentSystemIdentifier(nodeHandle);
/*      */     }
/*      */     
/* 1062 */     return super.getDocumentSystemIdentifier(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDocumentEncoding(int nodeHandle) {
/* 1068 */     if (this._dom != null) {
/* 1069 */       return this._dom.getDocumentEncoding(nodeHandle);
/*      */     }
/*      */     
/* 1072 */     return super.getDocumentEncoding(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDocumentStandalone(int nodeHandle) {
/* 1078 */     if (this._dom != null) {
/* 1079 */       return this._dom.getDocumentStandalone(nodeHandle);
/*      */     }
/*      */     
/* 1082 */     return super.getDocumentStandalone(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDocumentVersion(int documentHandle) {
/* 1088 */     if (this._dom != null) {
/* 1089 */       return this._dom.getDocumentVersion(documentHandle);
/*      */     }
/*      */     
/* 1092 */     return super.getDocumentVersion(documentHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDocumentAllDeclarationsProcessed() {
/* 1098 */     if (this._dom != null) {
/* 1099 */       return this._dom.getDocumentAllDeclarationsProcessed();
/*      */     }
/*      */     
/* 1102 */     return super.getDocumentAllDeclarationsProcessed();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDocumentTypeDeclarationSystemIdentifier() {
/* 1108 */     if (this._dom != null) {
/* 1109 */       return this._dom.getDocumentTypeDeclarationSystemIdentifier();
/*      */     }
/*      */     
/* 1112 */     return super.getDocumentTypeDeclarationSystemIdentifier();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDocumentTypeDeclarationPublicIdentifier() {
/* 1118 */     if (this._dom != null) {
/* 1119 */       return this._dom.getDocumentTypeDeclarationPublicIdentifier();
/*      */     }
/*      */     
/* 1122 */     return super.getDocumentTypeDeclarationPublicIdentifier();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getElementById(String elementId) {
/* 1128 */     if (this._dom != null) {
/* 1129 */       return this._dom.getElementById(elementId);
/*      */     }
/*      */     
/* 1132 */     return super.getElementById(elementId);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsPreStripping() {
/* 1138 */     if (this._dom != null) {
/* 1139 */       return this._dom.supportsPreStripping();
/*      */     }
/*      */     
/* 1142 */     return super.supportsPreStripping();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNodeAfter(int firstNodeHandle, int secondNodeHandle) {
/* 1148 */     if (this._dom != null) {
/* 1149 */       return this._dom.isNodeAfter(firstNodeHandle, secondNodeHandle);
/*      */     }
/*      */     
/* 1152 */     return super.isNodeAfter(firstNodeHandle, secondNodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCharacterElementContentWhitespace(int nodeHandle) {
/* 1158 */     if (this._dom != null) {
/* 1159 */       return this._dom.isCharacterElementContentWhitespace(nodeHandle);
/*      */     }
/*      */     
/* 1162 */     return super.isCharacterElementContentWhitespace(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDocumentAllDeclarationsProcessed(int documentHandle) {
/* 1168 */     if (this._dom != null) {
/* 1169 */       return this._dom.isDocumentAllDeclarationsProcessed(documentHandle);
/*      */     }
/*      */     
/* 1172 */     return super.isDocumentAllDeclarationsProcessed(documentHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttributeSpecified(int attributeHandle) {
/* 1178 */     if (this._dom != null) {
/* 1179 */       return this._dom.isAttributeSpecified(attributeHandle);
/*      */     }
/*      */     
/* 1182 */     return super.isAttributeSpecified(attributeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize) throws SAXException {
/* 1190 */     if (this._dom != null) {
/* 1191 */       this._dom.dispatchCharactersEvents(nodeHandle, ch, normalize);
/*      */     } else {
/*      */       
/* 1194 */       super.dispatchCharactersEvents(nodeHandle, ch, normalize);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchToEvents(int nodeHandle, ContentHandler ch) throws SAXException {
/* 1201 */     if (this._dom != null) {
/* 1202 */       this._dom.dispatchToEvents(nodeHandle, ch);
/*      */     } else {
/*      */       
/* 1205 */       super.dispatchToEvents(nodeHandle, ch);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Node getNode(int nodeHandle) {
/* 1211 */     if (this._dom != null) {
/* 1212 */       return this._dom.getNode(nodeHandle);
/*      */     }
/*      */     
/* 1215 */     return super.getNode(nodeHandle);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean needsTwoThreads() {
/* 1221 */     if (this._dom != null) {
/* 1222 */       return this._dom.needsTwoThreads();
/*      */     }
/*      */     
/* 1225 */     return super.needsTwoThreads();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ContentHandler getContentHandler() {
/* 1231 */     if (this._dom != null) {
/* 1232 */       return this._dom.getContentHandler();
/*      */     }
/*      */     
/* 1235 */     return super.getContentHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public LexicalHandler getLexicalHandler() {
/* 1241 */     if (this._dom != null) {
/* 1242 */       return this._dom.getLexicalHandler();
/*      */     }
/*      */     
/* 1245 */     return super.getLexicalHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/* 1251 */     if (this._dom != null) {
/* 1252 */       return this._dom.getEntityResolver();
/*      */     }
/*      */     
/* 1255 */     return super.getEntityResolver();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTDHandler getDTDHandler() {
/* 1261 */     if (this._dom != null) {
/* 1262 */       return this._dom.getDTDHandler();
/*      */     }
/*      */     
/* 1265 */     return super.getDTDHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ErrorHandler getErrorHandler() {
/* 1271 */     if (this._dom != null) {
/* 1272 */       return this._dom.getErrorHandler();
/*      */     }
/*      */     
/* 1275 */     return super.getErrorHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DeclHandler getDeclHandler() {
/* 1281 */     if (this._dom != null) {
/* 1282 */       return this._dom.getDeclHandler();
/*      */     }
/*      */     
/* 1285 */     return super.getDeclHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendChild(int newChild, boolean clone, boolean cloneDepth) {
/* 1291 */     if (this._dom != null) {
/* 1292 */       this._dom.appendChild(newChild, clone, cloneDepth);
/*      */     } else {
/*      */       
/* 1295 */       super.appendChild(newChild, clone, cloneDepth);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void appendTextChild(String str) {
/* 1301 */     if (this._dom != null) {
/* 1302 */       this._dom.appendTextChild(str);
/*      */     } else {
/*      */       
/* 1305 */       super.appendTextChild(str);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SourceLocator getSourceLocatorFor(int node) {
/* 1311 */     if (this._dom != null) {
/* 1312 */       return this._dom.getSourceLocatorFor(node);
/*      */     }
/*      */     
/* 1315 */     return super.getSourceLocatorFor(node);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void documentRegistration() {
/* 1321 */     if (this._dom != null) {
/* 1322 */       this._dom.documentRegistration();
/*      */     } else {
/*      */       
/* 1325 */       super.documentRegistration();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void documentRelease() {
/* 1331 */     if (this._dom != null) {
/* 1332 */       this._dom.documentRelease();
/*      */     } else {
/*      */       
/* 1335 */       super.documentRelease();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void release() {
/* 1340 */     if (this._dom != null) {
/* 1341 */       this._dom.release();
/* 1342 */       this._dom = null;
/*      */     } 
/* 1344 */     super.release();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/AdaptiveResultTreeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */