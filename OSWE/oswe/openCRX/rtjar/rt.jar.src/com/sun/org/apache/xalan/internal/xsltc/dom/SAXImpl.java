/*      */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.DOMEnhancedForDTM;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.StripFilter;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.TransletException;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
/*      */ import com.sun.org.apache.xml.internal.dtm.Axis;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIterNodeList;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeProxy;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.EmptyIterator;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.ExpandedNameTable;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;
/*      */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*      */ import com.sun.org.apache.xml.internal.serializer.ToXMLSAXHandler;
/*      */ import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.DocumentType;
/*      */ import org.w3c.dom.Entity;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class SAXImpl
/*      */   extends SAX2DTM2
/*      */   implements DOMEnhancedForDTM, DOMBuilder
/*      */ {
/*   81 */   private int _uriCount = 0;
/*      */ 
/*      */   
/*      */   private int[] _xmlSpaceStack;
/*      */ 
/*      */   
/*   87 */   private int _idx = 1;
/*      */   
/*      */   private boolean _preserve = false;
/*      */   
/*      */   private static final String XML_PREFIX = "xml";
/*      */   
/*      */   private static final String XMLSPACE_STRING = "xml:space";
/*      */   
/*      */   private static final String PRESERVE_STRING = "preserve";
/*      */   private static final String XML_URI = "http://www.w3.org/XML/1998/namespace";
/*      */   private boolean _escaping = true;
/*      */   private boolean _disableEscaping = false;
/*   99 */   private int _textNodeToProcess = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String EMPTYSTRING = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   private static final DTMAxisIterator EMPTYITERATOR = EmptyIterator.getInstance();
/*      */   
/*  111 */   private int _namesSize = -1;
/*      */ 
/*      */   
/*  114 */   private Map<Integer, Integer> _nsIndex = new HashMap<>();
/*      */ 
/*      */   
/*  117 */   private int _size = 0;
/*      */ 
/*      */   
/*  120 */   private BitArray _dontEscape = null;
/*      */ 
/*      */ 
/*      */   
/*  124 */   private static int _documentURIIndex = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private Document _document;
/*      */ 
/*      */ 
/*      */   
/*  132 */   private Map<Node, Integer> _node2Ids = null;
/*      */ 
/*      */   
/*      */   private boolean _hasDOMSource = false;
/*      */ 
/*      */   
/*      */   private XSLTCDTMManager _dtmManager;
/*      */ 
/*      */   
/*      */   private Node[] _nodes;
/*      */ 
/*      */   
/*      */   private NodeList[] _nodeLists;
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentURI(String uri) {
/*  149 */     if (uri != null) {
/*  150 */       setDocumentBaseURI(SystemIDResolver.getAbsoluteURI(uri));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDocumentURI() {
/*  158 */     String baseURI = getDocumentBaseURI();
/*  159 */     return (baseURI != null) ? baseURI : ("rtf" + _documentURIIndex++);
/*      */   }
/*      */   
/*      */   public String getDocumentURI(int node) {
/*  163 */     return getDocumentURI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupMapping(String[] names, String[] urisArray, int[] typesArray, String[] namespaces) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String lookupNamespace(int node, String prefix) throws TransletException {
/*  180 */     SAX2DTM2.AncestorIterator ancestors = new SAX2DTM2.AncestorIterator(this);
/*      */     
/*  182 */     if (isElement(node)) {
/*  183 */       ancestors.includeSelf();
/*      */     }
/*      */     
/*  186 */     ancestors.setStartNode(node); int anode;
/*  187 */     while ((anode = ancestors.next()) != -1) {
/*  188 */       DTMDefaultBaseIterators.NamespaceIterator namespaces = new DTMDefaultBaseIterators.NamespaceIterator(this);
/*      */       
/*  190 */       namespaces.setStartNode(anode); int nsnode;
/*  191 */       while ((nsnode = namespaces.next()) != -1) {
/*  192 */         if (getLocalName(nsnode).equals(prefix)) {
/*  193 */           return getNodeValue(nsnode);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  198 */     BasisLibrary.runTimeError("NAMESPACE_PREFIX_ERR", prefix);
/*  199 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isElement(int node) {
/*  206 */     return (getNodeType(node) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttribute(int node) {
/*  213 */     return (getNodeType(node) == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSize() {
/*  220 */     return getNumberOfNodes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFilter(StripFilter filter) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean lessThan(int node1, int node2) {
/*  234 */     if (node1 == -1) {
/*  235 */       return false;
/*      */     }
/*      */     
/*  238 */     if (node2 == -1) {
/*  239 */       return true;
/*      */     }
/*      */     
/*  242 */     return (node1 < node2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node makeNode(int index) {
/*  249 */     if (this._nodes == null) {
/*  250 */       this._nodes = new Node[this._namesSize];
/*      */     }
/*      */     
/*  253 */     int nodeID = makeNodeIdentity(index);
/*  254 */     if (nodeID < 0) {
/*  255 */       return null;
/*      */     }
/*  257 */     if (nodeID < this._nodes.length) {
/*  258 */       return (this._nodes[nodeID] != null) ? this._nodes[nodeID] : (this._nodes[nodeID] = new DTMNodeProxy(this, index));
/*      */     }
/*      */ 
/*      */     
/*  262 */     return new DTMNodeProxy(this, index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node makeNode(DTMAxisIterator iter) {
/*  271 */     return makeNode(iter.next());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NodeList makeNodeList(int index) {
/*  278 */     if (this._nodeLists == null) {
/*  279 */       this._nodeLists = new NodeList[this._namesSize];
/*      */     }
/*      */     
/*  282 */     int nodeID = makeNodeIdentity(index);
/*  283 */     if (nodeID < 0) {
/*  284 */       return null;
/*      */     }
/*  286 */     if (nodeID < this._nodeLists.length) {
/*  287 */       return (this._nodeLists[nodeID] != null) ? this._nodeLists[nodeID] : (this._nodeLists[nodeID] = new DTMAxisIterNodeList(this, new DTMDefaultBaseIterators.SingletonIterator(this, index)));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  292 */     return new DTMAxisIterNodeList(this, new DTMDefaultBaseIterators.SingletonIterator(this, index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NodeList makeNodeList(DTMAxisIterator iter) {
/*  301 */     return new DTMAxisIterNodeList(this, iter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class TypedNamespaceIterator
/*      */     extends DTMDefaultBaseIterators.NamespaceIterator
/*      */   {
/*      */     private String _nsPrefix;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TypedNamespaceIterator(int nodeType) {
/*  320 */       if (SAXImpl.this.m_expandedNameTable != null) {
/*  321 */         this._nsPrefix = SAXImpl.this.m_expandedNameTable.getLocalName(nodeType);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  331 */       if (this._nsPrefix == null || this._nsPrefix.length() == 0) {
/*  332 */         return -1;
/*      */       }
/*  334 */       int node = -1;
/*  335 */       for (node = super.next(); node != -1; node = super.next()) {
/*  336 */         if (this._nsPrefix.compareTo(SAXImpl.this.getLocalName(node)) == 0) {
/*  337 */           return returnNode(node);
/*      */         }
/*      */       } 
/*  340 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class NodeValueIterator
/*      */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*      */   {
/*      */     private DTMAxisIterator _source;
/*      */     
/*      */     private String _value;
/*      */     
/*      */     private boolean _op;
/*      */     
/*      */     private final boolean _isReverse;
/*      */     
/*  357 */     private int _returnType = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     public NodeValueIterator(DTMAxisIterator source, int returnType, String value, boolean op) {
/*  362 */       this._source = source;
/*  363 */       this._returnType = returnType;
/*  364 */       this._value = value;
/*  365 */       this._op = op;
/*  366 */       this._isReverse = source.isReverse();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isReverse() {
/*  371 */       return this._isReverse;
/*      */     }
/*      */ 
/*      */     
/*      */     public DTMAxisIterator cloneIterator() {
/*      */       try {
/*  377 */         NodeValueIterator clone = (NodeValueIterator)clone();
/*  378 */         clone._isRestartable = false;
/*  379 */         clone._source = this._source.cloneIterator();
/*  380 */         clone._value = this._value;
/*  381 */         clone._op = this._op;
/*  382 */         return clone.reset();
/*      */       }
/*  384 */       catch (CloneNotSupportedException e) {
/*  385 */         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e
/*  386 */             .toString());
/*  387 */         return null;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setRestartable(boolean isRestartable) {
/*  393 */       this._isRestartable = isRestartable;
/*  394 */       this._source.setRestartable(isRestartable);
/*      */     }
/*      */ 
/*      */     
/*      */     public DTMAxisIterator reset() {
/*  399 */       this._source.reset();
/*  400 */       return resetPosition();
/*      */     }
/*      */ 
/*      */     
/*      */     public int next() {
/*      */       int node;
/*  406 */       while ((node = this._source.next()) != -1) {
/*  407 */         String val = SAXImpl.this.getStringValueX(node);
/*  408 */         if (this._value.equals(val) == this._op) {
/*  409 */           if (this._returnType == 0) {
/*  410 */             return returnNode(node);
/*      */           }
/*      */           
/*  413 */           return returnNode(SAXImpl.this.getParent(node));
/*      */         } 
/*      */       } 
/*      */       
/*  417 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/*  422 */       if (this._isRestartable) {
/*  423 */         this._source.setStartNode(this._startNode = node);
/*  424 */         return resetPosition();
/*      */       } 
/*  426 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setMark() {
/*  431 */       this._source.setMark();
/*      */     }
/*      */ 
/*      */     
/*      */     public void gotoMark() {
/*  436 */       this._source.gotoMark();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iterator, int type, String value, boolean op) {
/*  443 */     return new NodeValueIterator(iterator, type, value, op);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node) {
/*  451 */     return new DupFilterIterator(source);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getIterator() {
/*  461 */     return new DTMDefaultBaseIterators.SingletonIterator(this, getDocument(), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNSType(int node) {
/*  469 */     String s = getNamespaceURI(node);
/*  470 */     if (s == null) {
/*  471 */       return 0;
/*      */     }
/*  473 */     int eType = getIdForNamespace(s);
/*  474 */     return ((Integer)this._nsIndex.get(new Integer(eType))).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNamespaceType(int node) {
/*  484 */     return super.getNamespaceType(node);
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
/*      */   public int getGeneralizedType(String name) {
/*  508 */     return getGeneralizedType(name, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getGeneralizedType(String name, boolean searchOnly) {
/*      */     int code;
/*  515 */     String ns = null;
/*  516 */     int index = -1;
/*      */ 
/*      */ 
/*      */     
/*  520 */     if ((index = name.lastIndexOf(":")) > -1) {
/*  521 */       ns = name.substring(0, index);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  526 */     int lNameStartIdx = index + 1;
/*      */ 
/*      */ 
/*      */     
/*  530 */     if (name.charAt(lNameStartIdx) == '@') {
/*  531 */       code = 2;
/*  532 */       lNameStartIdx++;
/*      */     } else {
/*      */       
/*  535 */       code = 1;
/*      */     } 
/*      */ 
/*      */     
/*  539 */     String lName = (lNameStartIdx == 0) ? name : name.substring(lNameStartIdx);
/*      */     
/*  541 */     return this.m_expandedNameTable.getExpandedTypeID(ns, lName, code, searchOnly);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short[] getMapping(String[] names, String[] uris, int[] types) {
/*  551 */     if (this._namesSize < 0) {
/*  552 */       return getMapping2(names, uris, types);
/*      */     }
/*      */ 
/*      */     
/*  556 */     int namesLength = names.length;
/*  557 */     int exLength = this.m_expandedNameTable.getSize();
/*      */     
/*  559 */     short[] result = new short[exLength];
/*      */     
/*      */     int i;
/*  562 */     for (i = 0; i < 14; i++) {
/*  563 */       result[i] = (short)i;
/*      */     }
/*      */     
/*  566 */     for (i = 14; i < exLength; i++) {
/*  567 */       result[i] = this.m_expandedNameTable.getType(i);
/*      */     }
/*      */ 
/*      */     
/*  571 */     for (i = 0; i < namesLength; i++) {
/*  572 */       int genType = this.m_expandedNameTable.getExpandedTypeID(uris[i], names[i], types[i], true);
/*      */ 
/*      */ 
/*      */       
/*  576 */       if (genType >= 0 && genType < exLength) {
/*  577 */         result[genType] = (short)(i + 14);
/*      */       }
/*      */     } 
/*      */     
/*  581 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getReverseMapping(String[] names, String[] uris, int[] types) {
/*  590 */     int[] result = new int[names.length + 14];
/*      */     
/*      */     int i;
/*  593 */     for (i = 0; i < 14; i++) {
/*  594 */       result[i] = i;
/*      */     }
/*      */ 
/*      */     
/*  598 */     for (i = 0; i < names.length; i++) {
/*  599 */       int type = this.m_expandedNameTable.getExpandedTypeID(uris[i], names[i], types[i], true);
/*  600 */       result[i + 14] = type;
/*      */     } 
/*  602 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private short[] getMapping2(String[] names, String[] uris, int[] types) {
/*  612 */     int namesLength = names.length;
/*  613 */     int exLength = this.m_expandedNameTable.getSize();
/*  614 */     int[] generalizedTypes = null;
/*  615 */     if (namesLength > 0) {
/*  616 */       generalizedTypes = new int[namesLength];
/*      */     }
/*      */     
/*  619 */     int resultLength = exLength;
/*      */     int i;
/*  621 */     for (i = 0; i < namesLength; i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  626 */       generalizedTypes[i] = this.m_expandedNameTable
/*  627 */         .getExpandedTypeID(uris[i], names[i], types[i], false);
/*      */ 
/*      */ 
/*      */       
/*  631 */       if (this._namesSize < 0 && generalizedTypes[i] >= resultLength) {
/*  632 */         resultLength = generalizedTypes[i] + 1;
/*      */       }
/*      */     } 
/*      */     
/*  636 */     short[] result = new short[resultLength];
/*      */ 
/*      */     
/*  639 */     for (i = 0; i < 14; i++) {
/*  640 */       result[i] = (short)i;
/*      */     }
/*      */     
/*  643 */     for (i = 14; i < exLength; i++) {
/*  644 */       result[i] = this.m_expandedNameTable.getType(i);
/*      */     }
/*      */ 
/*      */     
/*  648 */     for (i = 0; i < namesLength; i++) {
/*  649 */       int genType = generalizedTypes[i];
/*  650 */       if (genType >= 0 && genType < resultLength) {
/*  651 */         result[genType] = (short)(i + 14);
/*      */       }
/*      */     } 
/*      */     
/*  655 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short[] getNamespaceMapping(String[] namespaces) {
/*  663 */     int nsLength = namespaces.length;
/*  664 */     int mappingLength = this._uriCount;
/*      */     
/*  666 */     short[] result = new short[mappingLength];
/*      */     
/*      */     int i;
/*  669 */     for (i = 0; i < mappingLength; i++) {
/*  670 */       result[i] = -1;
/*      */     }
/*      */     
/*  673 */     for (i = 0; i < nsLength; i++) {
/*  674 */       int eType = getIdForNamespace(namespaces[i]);
/*  675 */       Integer type = this._nsIndex.get(Integer.valueOf(eType));
/*  676 */       if (type != null) {
/*  677 */         result[type.intValue()] = (short)i;
/*      */       }
/*      */     } 
/*      */     
/*  681 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short[] getReverseNamespaceMapping(String[] namespaces) {
/*  690 */     int length = namespaces.length;
/*  691 */     short[] result = new short[length];
/*      */     
/*  693 */     for (int i = 0; i < length; i++) {
/*  694 */       int eType = getIdForNamespace(namespaces[i]);
/*  695 */       Integer type = this._nsIndex.get(Integer.valueOf(eType));
/*  696 */       result[i] = (type == null) ? -1 : type.shortValue();
/*      */     } 
/*      */     
/*  699 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXImpl(XSLTCDTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, boolean buildIdIndex) {
/*  710 */     this(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, 512, buildIdIndex, false);
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
/*      */   public SAXImpl(XSLTCDTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean buildIdIndex, boolean newNameTable) {
/*  724 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, blocksize, false, buildIdIndex, newNameTable);
/*      */ 
/*      */     
/*  727 */     this._dtmManager = mgr;
/*  728 */     this._size = blocksize;
/*      */ 
/*      */     
/*  731 */     this._xmlSpaceStack = new int[(blocksize <= 64) ? 4 : 64];
/*      */ 
/*      */     
/*  734 */     this._xmlSpaceStack[0] = 0;
/*      */ 
/*      */ 
/*      */     
/*  738 */     if (source instanceof DOMSource) {
/*  739 */       this._hasDOMSource = true;
/*  740 */       DOMSource domsrc = (DOMSource)source;
/*  741 */       Node node = domsrc.getNode();
/*  742 */       if (node instanceof Document) {
/*  743 */         this._document = (Document)node;
/*      */       } else {
/*      */         
/*  746 */         this._document = node.getOwnerDocument();
/*      */       } 
/*  748 */       this._node2Ids = new HashMap<>();
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
/*      */   public void migrateTo(DTMManager manager) {
/*  760 */     super.migrateTo(manager);
/*  761 */     if (manager instanceof XSLTCDTMManager) {
/*  762 */       this._dtmManager = (XSLTCDTMManager)manager;
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
/*      */   public int getElementById(String idString) {
/*  774 */     Node node = this._document.getElementById(idString);
/*  775 */     if (node != null) {
/*  776 */       Integer id = this._node2Ids.get(node);
/*  777 */       return (id != null) ? id.intValue() : -1;
/*      */     } 
/*      */     
/*  780 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasDOMSource() {
/*  789 */     return this._hasDOMSource;
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
/*      */   private void xmlSpaceDefine(String val, int node) {
/*  802 */     boolean setting = val.equals("preserve");
/*  803 */     if (setting != this._preserve) {
/*  804 */       this._xmlSpaceStack[this._idx++] = node;
/*  805 */       this._preserve = setting;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void xmlSpaceRevert(int node) {
/*  815 */     if (node == this._xmlSpaceStack[this._idx - 1]) {
/*  816 */       this._idx--;
/*  817 */       this._preserve = !this._preserve;
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
/*      */   protected boolean getShouldStripWhitespace() {
/*  829 */     return this._preserve ? false : super.getShouldStripWhitespace();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleTextEscaping() {
/*  836 */     if (this._disableEscaping && this._textNodeToProcess != -1 && 
/*  837 */       _type(this._textNodeToProcess) == 3) {
/*  838 */       if (this._dontEscape == null) {
/*  839 */         this._dontEscape = new BitArray(this._size);
/*      */       }
/*      */ 
/*      */       
/*  843 */       if (this._textNodeToProcess >= this._dontEscape.size()) {
/*  844 */         this._dontEscape.resize(this._dontEscape.size() * 2);
/*      */       }
/*      */       
/*  847 */       this._dontEscape.setBit(this._textNodeToProcess);
/*  848 */       this._disableEscaping = false;
/*      */     } 
/*  850 */     this._textNodeToProcess = -1;
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
/*      */   public void characters(char[] ch, int start, int length) throws SAXException {
/*  863 */     super.characters(ch, start, length);
/*      */     
/*  865 */     this._disableEscaping = !this._escaping;
/*  866 */     this._textNodeToProcess = getNumberOfNodes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startDocument() throws SAXException {
/*  874 */     super.startDocument();
/*      */     
/*  876 */     this._nsIndex.put(Integer.valueOf(0), Integer.valueOf(this._uriCount++));
/*  877 */     definePrefixAndUri("xml", "http://www.w3.org/XML/1998/namespace");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endDocument() throws SAXException {
/*  885 */     super.endDocument();
/*      */     
/*  887 */     handleTextEscaping();
/*  888 */     this._namesSize = this.m_expandedNameTable.getSize();
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
/*      */   public void startElement(String uri, String localName, String qname, Attributes attributes, Node node) throws SAXException {
/*  900 */     startElement(uri, localName, qname, attributes);
/*      */     
/*  902 */     if (this.m_buildIdIndex) {
/*  903 */       this._node2Ids.put(node, new Integer(this.m_parents.peek()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElement(String uri, String localName, String qname, Attributes attributes) throws SAXException {
/*  914 */     super.startElement(uri, localName, qname, attributes);
/*      */     
/*  916 */     handleTextEscaping();
/*      */     
/*  918 */     if (this.m_wsfilter != null) {
/*      */ 
/*      */ 
/*      */       
/*  922 */       int index = attributes.getIndex("xml:space");
/*  923 */       if (index >= 0) {
/*  924 */         xmlSpaceDefine(attributes.getValue(index), this.m_parents.peek());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement(String namespaceURI, String localName, String qname) throws SAXException {
/*  935 */     super.endElement(namespaceURI, localName, qname);
/*      */     
/*  937 */     handleTextEscaping();
/*      */ 
/*      */     
/*  940 */     if (this.m_wsfilter != null) {
/*  941 */       xmlSpaceRevert(this.m_previous);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processingInstruction(String target, String data) throws SAXException {
/*  951 */     super.processingInstruction(target, data);
/*  952 */     handleTextEscaping();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/*  962 */     super.ignorableWhitespace(ch, start, length);
/*  963 */     this._textNodeToProcess = getNumberOfNodes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*  972 */     super.startPrefixMapping(prefix, uri);
/*  973 */     handleTextEscaping();
/*      */     
/*  975 */     definePrefixAndUri(prefix, uri);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void definePrefixAndUri(String prefix, String uri) throws SAXException {
/*  982 */     Integer eType = new Integer(getIdForNamespace(uri));
/*  983 */     if (this._nsIndex.get(eType) == null) {
/*  984 */       this._nsIndex.put(eType, Integer.valueOf(this._uriCount++));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void comment(char[] ch, int start, int length) throws SAXException {
/*  994 */     super.comment(ch, start, length);
/*  995 */     handleTextEscaping();
/*      */   }
/*      */   
/*      */   public boolean setEscaping(boolean value) {
/*  999 */     boolean temp = this._escaping;
/* 1000 */     this._escaping = value;
/* 1001 */     return temp;
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
/*      */   public void print(int node, int level) {
/* 1013 */     switch (getNodeType(node)) {
/*      */       
/*      */       case 0:
/*      */       case 9:
/* 1017 */         print(getFirstChild(node), level);
/*      */         return;
/*      */       case 3:
/*      */       case 7:
/*      */       case 8:
/* 1022 */         System.out.print(getStringValueX(node));
/*      */         return;
/*      */     } 
/* 1025 */     String name = getNodeName(node);
/* 1026 */     System.out.print("<" + name); int a;
/* 1027 */     for (a = getFirstAttribute(node); a != -1; a = getNextAttribute(a))
/*      */     {
/* 1029 */       System.out.print("\n" + getNodeName(a) + "=\"" + getStringValueX(a) + "\"");
/*      */     }
/* 1031 */     System.out.print('>'); int child;
/* 1032 */     for (child = getFirstChild(node); child != -1; 
/* 1033 */       child = getNextSibling(child)) {
/* 1034 */       print(child, level + 1);
/*      */     }
/* 1036 */     System.out.println("</" + name + '>');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNodeName(int node) {
/* 1047 */     int nodeh = node;
/* 1048 */     short type = getNodeType(nodeh);
/* 1049 */     switch (type) {
/*      */       
/*      */       case 0:
/*      */       case 3:
/*      */       case 8:
/*      */       case 9:
/* 1055 */         return "";
/*      */       case 13:
/* 1057 */         return getLocalName(nodeh);
/*      */     } 
/* 1059 */     return super.getNodeName(nodeh);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespaceName(int node) {
/* 1068 */     if (node == -1) {
/* 1069 */       return "";
/*      */     }
/*      */     
/*      */     String s;
/* 1073 */     return ((s = getNamespaceURI(node)) == null) ? "" : s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAttributeNode(int type, int element) {
/* 1082 */     int attr = getFirstAttribute(element);
/* 1083 */     for (; attr != -1; 
/* 1084 */       attr = getNextAttribute(attr)) {
/*      */       
/* 1086 */       if (getExpandedTypeID(attr) == type) return attr; 
/*      */     } 
/* 1088 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(int type, int element) {
/* 1096 */     int attr = getAttributeNode(type, element);
/* 1097 */     return (attr != -1) ? getStringValueX(attr) : "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeValue(String name, int element) {
/* 1105 */     return getAttributeValue(getGeneralizedType(name), element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getChildren(int node) {
/* 1113 */     return (new SAX2DTM2.ChildrenIterator(this)).setStartNode(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getTypedChildren(int type) {
/* 1122 */     return new SAX2DTM2.TypedChildrenIterator(this, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getAxisIterator(int axis) {
/* 1133 */     switch (axis) {
/*      */       
/*      */       case 13:
/* 1136 */         return new DTMDefaultBaseIterators.SingletonIterator(this);
/*      */       case 3:
/* 1138 */         return new SAX2DTM2.ChildrenIterator(this);
/*      */       case 10:
/* 1140 */         return new SAX2DTM2.ParentIterator(this);
/*      */       case 0:
/* 1142 */         return new SAX2DTM2.AncestorIterator(this);
/*      */       case 1:
/* 1144 */         return (new SAX2DTM2.AncestorIterator(this)).includeSelf();
/*      */       case 2:
/* 1146 */         return new SAX2DTM2.AttributeIterator(this);
/*      */       case 4:
/* 1148 */         return new SAX2DTM2.DescendantIterator(this);
/*      */       case 5:
/* 1150 */         return (new SAX2DTM2.DescendantIterator(this)).includeSelf();
/*      */       case 6:
/* 1152 */         return new SAX2DTM2.FollowingIterator(this);
/*      */       case 11:
/* 1154 */         return new SAX2DTM2.PrecedingIterator(this);
/*      */       case 7:
/* 1156 */         return new SAX2DTM2.FollowingSiblingIterator(this);
/*      */       case 12:
/* 1158 */         return new SAX2DTM2.PrecedingSiblingIterator(this);
/*      */       case 9:
/* 1160 */         return new DTMDefaultBaseIterators.NamespaceIterator(this);
/*      */       case 19:
/* 1162 */         return new DTMDefaultBaseIterators.RootIterator(this);
/*      */     } 
/* 1164 */     BasisLibrary.runTimeError("AXIS_SUPPORT_ERR", 
/* 1165 */         Axis.getNames(axis));
/*      */     
/* 1167 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getTypedAxisIterator(int axis, int type) {
/* 1177 */     if (axis == 3) {
/* 1178 */       return new SAX2DTM2.TypedChildrenIterator(this, type);
/*      */     }
/*      */     
/* 1181 */     if (type == -1) {
/* 1182 */       return EMPTYITERATOR;
/*      */     }
/*      */     
/* 1185 */     switch (axis) {
/*      */       
/*      */       case 13:
/* 1188 */         return new SAX2DTM2.TypedSingletonIterator(this, type);
/*      */       case 3:
/* 1190 */         return new SAX2DTM2.TypedChildrenIterator(this, type);
/*      */       case 10:
/* 1192 */         return (new SAX2DTM2.ParentIterator(this)).setNodeType(type);
/*      */       case 0:
/* 1194 */         return new SAX2DTM2.TypedAncestorIterator(this, type);
/*      */       case 1:
/* 1196 */         return (new SAX2DTM2.TypedAncestorIterator(this, type)).includeSelf();
/*      */       case 2:
/* 1198 */         return new SAX2DTM2.TypedAttributeIterator(this, type);
/*      */       case 4:
/* 1200 */         return new SAX2DTM2.TypedDescendantIterator(this, type);
/*      */       case 5:
/* 1202 */         return (new SAX2DTM2.TypedDescendantIterator(this, type)).includeSelf();
/*      */       case 6:
/* 1204 */         return new SAX2DTM2.TypedFollowingIterator(this, type);
/*      */       case 11:
/* 1206 */         return new SAX2DTM2.TypedPrecedingIterator(this, type);
/*      */       case 7:
/* 1208 */         return new SAX2DTM2.TypedFollowingSiblingIterator(this, type);
/*      */       case 12:
/* 1210 */         return new SAX2DTM2.TypedPrecedingSiblingIterator(this, type);
/*      */       case 9:
/* 1212 */         return new TypedNamespaceIterator(type);
/*      */       case 19:
/* 1214 */         return new SAX2DTM2.TypedRootIterator(this, type);
/*      */     } 
/* 1216 */     BasisLibrary.runTimeError("TYPED_AXIS_SUPPORT_ERR", 
/* 1217 */         Axis.getNames(axis));
/*      */     
/* 1219 */     return null;
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
/*      */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns) {
/* 1231 */     if (ns == -1) {
/* 1232 */       return EMPTYITERATOR;
/*      */     }
/*      */     
/* 1235 */     switch (axis) {
/*      */       case 3:
/* 1237 */         return new NamespaceChildrenIterator(ns);
/*      */       case 2:
/* 1239 */         return new NamespaceAttributeIterator(ns);
/*      */     } 
/* 1241 */     return new NamespaceWildcardIterator(axis, ns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class NamespaceWildcardIterator
/*      */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*      */   {
/*      */     protected int m_nsType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected DTMAxisIterator m_baseIterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NamespaceWildcardIterator(int axis, int nsType) {
/* 1272 */       this.m_nsType = nsType;
/*      */ 
/*      */ 
/*      */       
/* 1276 */       switch (axis) {
/*      */ 
/*      */         
/*      */         case 2:
/* 1280 */           this.m_baseIterator = SAXImpl.this.getAxisIterator(axis);
/*      */ 
/*      */ 
/*      */         
/*      */         case 9:
/* 1285 */           this.m_baseIterator = SAXImpl.this.getAxisIterator(axis);
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1290 */       this.m_baseIterator = SAXImpl.this.getTypedAxisIterator(axis, 1);
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
/*      */     
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1305 */       if (this._isRestartable) {
/* 1306 */         this._startNode = node;
/* 1307 */         this.m_baseIterator.setStartNode(node);
/* 1308 */         resetPosition();
/*      */       } 
/* 1310 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*      */       int node;
/* 1321 */       while ((node = this.m_baseIterator.next()) != -1) {
/*      */         
/* 1323 */         if (SAXImpl.this.getNSType(node) == this.m_nsType) {
/* 1324 */           return returnNode(node);
/*      */         }
/*      */       } 
/*      */       
/* 1328 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator cloneIterator() {
/*      */       try {
/* 1339 */         DTMAxisIterator nestedClone = this.m_baseIterator.cloneIterator();
/*      */         
/* 1341 */         NamespaceWildcardIterator clone = (NamespaceWildcardIterator)clone();
/*      */         
/* 1343 */         clone.m_baseIterator = nestedClone;
/* 1344 */         clone.m_nsType = this.m_nsType;
/* 1345 */         clone._isRestartable = false;
/*      */         
/* 1347 */         return clone;
/* 1348 */       } catch (CloneNotSupportedException e) {
/* 1349 */         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e
/* 1350 */             .toString());
/* 1351 */         return null;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isReverse() {
/* 1361 */       return this.m_baseIterator.isReverse();
/*      */     }
/*      */     
/*      */     public void setMark() {
/* 1365 */       this.m_baseIterator.setMark();
/*      */     }
/*      */     
/*      */     public void gotoMark() {
/* 1369 */       this.m_baseIterator.gotoMark();
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
/*      */   public final class NamespaceChildrenIterator
/*      */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*      */   {
/*      */     private final int _nsType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NamespaceChildrenIterator(int type) {
/* 1393 */       this._nsType = type;
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
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1406 */       if (node == 0) {
/* 1407 */         node = SAXImpl.this.getDocument();
/*      */       }
/*      */       
/* 1410 */       if (this._isRestartable) {
/* 1411 */         this._startNode = node;
/* 1412 */         this._currentNode = (node == -1) ? -1 : -2;
/*      */         
/* 1414 */         return resetPosition();
/*      */       } 
/*      */       
/* 1417 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1426 */       if (this._currentNode != -1) {
/*      */ 
/*      */         
/* 1429 */         int node = (-2 == this._currentNode) ? SAXImpl.this._firstch(SAXImpl.this.makeNodeIdentity(this._startNode)) : SAXImpl.this._nextsib(this._currentNode);
/* 1430 */         for (; node != -1; 
/* 1431 */           node = SAXImpl.this._nextsib(node)) {
/* 1432 */           int nodeHandle = SAXImpl.this.makeNodeHandle(node);
/*      */           
/* 1434 */           if (SAXImpl.this.getNSType(nodeHandle) == this._nsType) {
/* 1435 */             this._currentNode = node;
/*      */             
/* 1437 */             return returnNode(nodeHandle);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1442 */       return -1;
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
/*      */   public final class NamespaceAttributeIterator
/*      */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*      */   {
/*      */     private final int _nsType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NamespaceAttributeIterator(int nsType) {
/* 1465 */       this._nsType = nsType;
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
/*      */     public DTMAxisIterator setStartNode(int node) {
/* 1478 */       if (node == 0) {
/* 1479 */         node = SAXImpl.this.getDocument();
/*      */       }
/*      */       
/* 1482 */       if (this._isRestartable) {
/* 1483 */         int nsType = this._nsType;
/*      */         
/* 1485 */         this._startNode = node;
/*      */         
/* 1487 */         node = SAXImpl.this.getFirstAttribute(node);
/* 1488 */         for (; node != -1; 
/* 1489 */           node = SAXImpl.this.getNextAttribute(node)) {
/* 1490 */           if (SAXImpl.this.getNSType(node) == nsType) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/* 1495 */         this._currentNode = node;
/* 1496 */         return resetPosition();
/*      */       } 
/*      */       
/* 1499 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/* 1508 */       int node = this._currentNode;
/* 1509 */       int nsType = this._nsType;
/*      */ 
/*      */       
/* 1512 */       if (node == -1) {
/* 1513 */         return -1;
/*      */       }
/*      */       
/* 1516 */       int nextNode = SAXImpl.this.getNextAttribute(node);
/* 1517 */       for (; nextNode != -1; 
/* 1518 */         nextNode = SAXImpl.this.getNextAttribute(nextNode)) {
/* 1519 */         if (SAXImpl.this.getNSType(nextNode) == nsType) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/* 1524 */       this._currentNode = nextNode;
/*      */       
/* 1526 */       return returnNode(node);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getTypedDescendantIterator(int type) {
/* 1536 */     return new SAX2DTM2.TypedDescendantIterator(this, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getNthDescendant(int type, int n, boolean includeself) {
/* 1544 */     return new DTMDefaultBaseIterators.NthDescendantIterator(this, n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void characters(int node, SerializationHandler handler) throws TransletException {
/* 1553 */     if (node != -1) {
/*      */       try {
/* 1555 */         dispatchCharactersEvents(node, handler, false);
/* 1556 */       } catch (SAXException e) {
/* 1557 */         throw new TransletException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copy(DTMAxisIterator nodes, SerializationHandler handler) throws TransletException {
/*      */     int node;
/* 1569 */     while ((node = nodes.next()) != -1) {
/* 1570 */       copy(node, handler);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copy(SerializationHandler handler) throws TransletException {
/* 1579 */     copy(getDocument(), handler);
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
/*      */   public void copy(int node, SerializationHandler handler) throws TransletException {
/* 1592 */     copy(node, handler, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void copy(int node, SerializationHandler handler, boolean isChild) throws TransletException {
/* 1599 */     int nodeID = makeNodeIdentity(node);
/* 1600 */     int eType = _exptype2(nodeID);
/* 1601 */     int type = _exptype2Type(eType); try {
/*      */       int c; boolean oldEscapeSetting;
/*      */       boolean escapeBit;
/* 1604 */       switch (type) {
/*      */         
/*      */         case 0:
/*      */         case 9:
/* 1608 */           for (c = _firstch2(nodeID); c != -1; c = _nextsib2(c)) {
/* 1609 */             copy(makeNodeHandle(c), handler, true);
/*      */           }
/*      */           return;
/*      */         case 7:
/* 1613 */           copyPI(node, handler);
/*      */           return;
/*      */         case 8:
/* 1616 */           handler.comment(getStringValueX(node));
/*      */           return;
/*      */         case 3:
/* 1619 */           oldEscapeSetting = false;
/* 1620 */           escapeBit = false;
/*      */           
/* 1622 */           if (this._dontEscape != null) {
/* 1623 */             escapeBit = this._dontEscape.getBit(getNodeIdent(node));
/* 1624 */             if (escapeBit) {
/* 1625 */               oldEscapeSetting = handler.setEscaping(false);
/*      */             }
/*      */           } 
/*      */           
/* 1629 */           copyTextNode(nodeID, handler);
/*      */           
/* 1631 */           if (escapeBit) {
/* 1632 */             handler.setEscaping(oldEscapeSetting);
/*      */           }
/*      */           return;
/*      */         case 2:
/* 1636 */           copyAttribute(nodeID, eType, handler);
/*      */           return;
/*      */         case 13:
/* 1639 */           handler.namespaceAfterStartElement(getNodeNameX(node), getNodeValue(node));
/*      */           return;
/*      */       } 
/* 1642 */       if (type == 1)
/*      */       {
/*      */         
/* 1645 */         String name = copyElement(nodeID, eType, handler);
/*      */ 
/*      */         
/* 1648 */         copyNS(nodeID, handler, !isChild);
/* 1649 */         copyAttributes(nodeID, handler);
/*      */         int i;
/* 1651 */         for (i = _firstch2(nodeID); i != -1; i = _nextsib2(i)) {
/* 1652 */           copy(makeNodeHandle(i), handler, true);
/*      */         }
/*      */ 
/*      */         
/* 1656 */         handler.endElement(name);
/*      */       }
/*      */       else
/*      */       {
/* 1660 */         String uri = getNamespaceName(node);
/* 1661 */         if (uri.length() != 0) {
/* 1662 */           String prefix = getPrefix(node);
/* 1663 */           handler.namespaceAfterStartElement(prefix, uri);
/*      */         } 
/* 1665 */         handler.addAttribute(getNodeName(node), getNodeValue(node));
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1670 */     catch (Exception e) {
/* 1671 */       throw new TransletException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void copyPI(int node, SerializationHandler handler) throws TransletException {
/* 1681 */     String target = getNodeName(node);
/* 1682 */     String value = getStringValueX(node);
/*      */     
/*      */     try {
/* 1685 */       handler.processingInstruction(target, value);
/* 1686 */     } catch (Exception e) {
/* 1687 */       throw new TransletException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String shallowCopy(int node, SerializationHandler handler) throws TransletException {
/* 1697 */     int nodeID = makeNodeIdentity(node);
/* 1698 */     int exptype = _exptype2(nodeID);
/* 1699 */     int type = _exptype2Type(exptype);
/*      */     try {
/*      */       String name;
/* 1702 */       switch (type) {
/*      */         
/*      */         case 1:
/* 1705 */           name = copyElement(nodeID, exptype, handler);
/* 1706 */           copyNS(nodeID, handler, true);
/* 1707 */           return name;
/*      */         case 0:
/*      */         case 9:
/* 1710 */           return "";
/*      */         case 3:
/* 1712 */           copyTextNode(nodeID, handler);
/* 1713 */           return null;
/*      */         case 7:
/* 1715 */           copyPI(node, handler);
/* 1716 */           return null;
/*      */         case 8:
/* 1718 */           handler.comment(getStringValueX(node));
/* 1719 */           return null;
/*      */         case 13:
/* 1721 */           handler.namespaceAfterStartElement(getNodeNameX(node), getNodeValue(node));
/* 1722 */           return null;
/*      */         case 2:
/* 1724 */           copyAttribute(nodeID, exptype, handler);
/* 1725 */           return null;
/*      */       } 
/* 1727 */       String uri1 = getNamespaceName(node);
/* 1728 */       if (uri1.length() != 0) {
/* 1729 */         String prefix = getPrefix(node);
/* 1730 */         handler.namespaceAfterStartElement(prefix, uri1);
/*      */       } 
/* 1732 */       handler.addAttribute(getNodeName(node), getNodeValue(node));
/* 1733 */       return null;
/*      */     }
/* 1735 */     catch (Exception e) {
/* 1736 */       throw new TransletException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLanguage(int node) {
/* 1745 */     int parent = node;
/* 1746 */     while (-1 != parent) {
/* 1747 */       if (1 == getNodeType(parent)) {
/* 1748 */         int langAttr = getAttributeNode(parent, "http://www.w3.org/XML/1998/namespace", "lang");
/*      */         
/* 1750 */         if (-1 != langAttr) {
/* 1751 */           return getNodeValue(langAttr);
/*      */         }
/*      */       } 
/*      */       
/* 1755 */       parent = getParent(parent);
/*      */     } 
/* 1757 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DOMBuilder getBuilder() {
/* 1767 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SerializationHandler getOutputDomBuilder() {
/* 1776 */     return new ToXMLSAXHandler(this, "UTF-8");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DOM getResultTreeFrag(int initSize, int rtfType) {
/* 1784 */     return getResultTreeFrag(initSize, rtfType, true);
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
/*      */   public DOM getResultTreeFrag(int initSize, int rtfType, boolean addToManager) {
/* 1797 */     if (rtfType == 0) {
/* 1798 */       if (addToManager) {
/* 1799 */         int dtmPos = this._dtmManager.getFirstFreeDTMID();
/* 1800 */         SimpleResultTreeImpl rtf = new SimpleResultTreeImpl(this._dtmManager, dtmPos << 16);
/*      */         
/* 1802 */         this._dtmManager.addDTM(rtf, dtmPos, 0);
/* 1803 */         return rtf;
/*      */       } 
/*      */       
/* 1806 */       return new SimpleResultTreeImpl(this._dtmManager, 0);
/*      */     } 
/*      */     
/* 1809 */     if (rtfType == 1) {
/* 1810 */       if (addToManager) {
/* 1811 */         int dtmPos = this._dtmManager.getFirstFreeDTMID();
/* 1812 */         AdaptiveResultTreeImpl rtf = new AdaptiveResultTreeImpl(this._dtmManager, dtmPos << 16, this.m_wsfilter, initSize, this.m_buildIdIndex);
/*      */ 
/*      */         
/* 1815 */         this._dtmManager.addDTM(rtf, dtmPos, 0);
/* 1816 */         return rtf;
/*      */       } 
/*      */ 
/*      */       
/* 1820 */       return new AdaptiveResultTreeImpl(this._dtmManager, 0, this.m_wsfilter, initSize, this.m_buildIdIndex);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1825 */     return (DOM)this._dtmManager.getDTM((Source)null, true, this.m_wsfilter, true, false, false, initSize, this.m_buildIdIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Integer> getElementsWithIDs() {
/* 1836 */     return this.m_idAttributes;
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
/*      */   public String getUnparsedEntityURI(String name) {
/* 1848 */     if (this._document != null) {
/* 1849 */       String uri = "";
/* 1850 */       DocumentType doctype = this._document.getDoctype();
/* 1851 */       if (doctype != null) {
/* 1852 */         NamedNodeMap entities = doctype.getEntities();
/*      */         
/* 1854 */         if (entities == null) {
/* 1855 */           return uri;
/*      */         }
/*      */         
/* 1858 */         Entity entity = (Entity)entities.getNamedItem(name);
/*      */         
/* 1860 */         if (entity == null) {
/* 1861 */           return uri;
/*      */         }
/*      */         
/* 1864 */         String notationName = entity.getNotationName();
/* 1865 */         if (notationName != null) {
/* 1866 */           uri = entity.getSystemId();
/* 1867 */           if (uri == null) {
/* 1868 */             uri = entity.getPublicId();
/*      */           }
/*      */         } 
/*      */       } 
/* 1872 */       return uri;
/*      */     } 
/*      */     
/* 1875 */     return super.getUnparsedEntityURI(name);
/*      */   }
/*      */ 
/*      */   
/*      */   public void release() {
/* 1880 */     this._dtmManager.release(this, true);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/SAXImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */