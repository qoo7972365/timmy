/*      */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.StripFilter;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.TransletException;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMAxisTraverser;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault;
/*      */ import com.sun.org.apache.xml.internal.serializer.EmptySerializer;
/*      */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLString;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLStringDefault;
/*      */ import java.util.Map;
/*      */ import javax.xml.transform.SourceLocator;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
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
/*      */ public class SimpleResultTreeImpl
/*      */   extends EmptySerializer
/*      */   implements DOM, DTM
/*      */ {
/*      */   public final class SimpleIterator
/*      */     extends DTMAxisIteratorBase
/*      */   {
/*      */     static final int DIRECTION_UP = 0;
/*      */     static final int DIRECTION_DOWN = 1;
/*      */     static final int NO_TYPE = -1;
/*   79 */     int _direction = 1;
/*      */     
/*   81 */     int _type = -1;
/*      */     
/*      */     int _currentNode;
/*      */ 
/*      */     
/*      */     public SimpleIterator() {}
/*      */ 
/*      */     
/*      */     public SimpleIterator(int direction) {
/*   90 */       this._direction = direction;
/*      */     }
/*      */ 
/*      */     
/*      */     public SimpleIterator(int direction, int type) {
/*   95 */       this._direction = direction;
/*   96 */       this._type = type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() {
/*  103 */       if (this._direction == 1) {
/*  104 */         while (this._currentNode < 2) {
/*  105 */           if (this._type != -1) {
/*  106 */             if ((this._currentNode == 0 && this._type == 0) || (this._currentNode == 1 && this._type == 3))
/*      */             {
/*  108 */               return returnNode(SimpleResultTreeImpl.this.getNodeHandle(this._currentNode++));
/*      */             }
/*  110 */             this._currentNode++;
/*      */             continue;
/*      */           } 
/*  113 */           return returnNode(SimpleResultTreeImpl.this.getNodeHandle(this._currentNode++));
/*      */         } 
/*      */         
/*  116 */         return -1;
/*      */       } 
/*      */ 
/*      */       
/*  120 */       while (this._currentNode >= 0) {
/*  121 */         if (this._type != -1) {
/*  122 */           if ((this._currentNode == 0 && this._type == 0) || (this._currentNode == 1 && this._type == 3))
/*      */           {
/*  124 */             return returnNode(SimpleResultTreeImpl.this.getNodeHandle(this._currentNode--));
/*      */           }
/*  126 */           this._currentNode--;
/*      */           continue;
/*      */         } 
/*  129 */         return returnNode(SimpleResultTreeImpl.this.getNodeHandle(this._currentNode--));
/*      */       } 
/*      */       
/*  132 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int nodeHandle) {
/*  138 */       int nodeID = SimpleResultTreeImpl.this.getNodeIdent(nodeHandle);
/*  139 */       this._startNode = nodeID;
/*      */ 
/*      */       
/*  142 */       if (!this._includeSelf && nodeID != -1) {
/*  143 */         if (this._direction == 1) {
/*  144 */           nodeID++;
/*  145 */         } else if (this._direction == 0) {
/*  146 */           nodeID--;
/*      */         } 
/*      */       }
/*  149 */       this._currentNode = nodeID;
/*  150 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setMark() {
/*  155 */       this._markedNode = this._currentNode;
/*      */     }
/*      */ 
/*      */     
/*      */     public void gotoMark() {
/*  160 */       this._currentNode = this._markedNode;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final class SingletonIterator
/*      */     extends DTMAxisIteratorBase
/*      */   {
/*      */     static final int NO_TYPE = -1;
/*      */     
/*  171 */     int _type = -1;
/*      */     
/*      */     int _currentNode;
/*      */ 
/*      */     
/*      */     public SingletonIterator() {}
/*      */ 
/*      */     
/*      */     public SingletonIterator(int type) {
/*  180 */       this._type = type;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setMark() {
/*  185 */       this._markedNode = this._currentNode;
/*      */     }
/*      */ 
/*      */     
/*      */     public void gotoMark() {
/*  190 */       this._currentNode = this._markedNode;
/*      */     }
/*      */ 
/*      */     
/*      */     public DTMAxisIterator setStartNode(int nodeHandle) {
/*  195 */       this._currentNode = this._startNode = SimpleResultTreeImpl.this.getNodeIdent(nodeHandle);
/*  196 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public int next() {
/*  201 */       if (this._currentNode == -1) {
/*  202 */         return -1;
/*      */       }
/*  204 */       this._currentNode = -1;
/*      */       
/*  206 */       if (this._type != -1) {
/*  207 */         if ((this._currentNode == 0 && this._type == 0) || (this._currentNode == 1 && this._type == 3))
/*      */         {
/*  209 */           return SimpleResultTreeImpl.this.getNodeHandle(this._currentNode);
/*      */         }
/*      */       } else {
/*  212 */         return SimpleResultTreeImpl.this.getNodeHandle(this._currentNode);
/*      */       } 
/*  214 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  220 */   private static final DTMAxisIterator EMPTY_ITERATOR = new DTMAxisIteratorBase() {
/*      */       public DTMAxisIterator reset() {
/*  222 */         return this;
/*  223 */       } public DTMAxisIterator setStartNode(int node) { return this; } public int next() {
/*  224 */         return -1;
/*      */       }
/*      */       public void setMark() {}
/*  227 */       public int getLast() { return 0; } public void gotoMark() {} public int getPosition() {
/*  228 */         return 0; } public DTMAxisIterator cloneIterator() {
/*  229 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setRestartable(boolean isRestartable) {}
/*      */     };
/*      */ 
/*      */   
/*      */   public static final int RTF_ROOT = 0;
/*      */   
/*      */   public static final int RTF_TEXT = 1;
/*      */   
/*      */   public static final int NUMBER_OF_NODES = 2;
/*      */   
/*  244 */   private static int _documentURIIndex = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String EMPTY_STR = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private String _text;
/*      */ 
/*      */ 
/*      */   
/*      */   protected String[] _textArray;
/*      */ 
/*      */   
/*      */   protected XSLTCDTMManager _dtmManager;
/*      */ 
/*      */   
/*  262 */   protected int _size = 0;
/*      */ 
/*      */   
/*      */   private int _documentID;
/*      */ 
/*      */   
/*  268 */   private BitArray _dontEscape = null;
/*      */ 
/*      */   
/*      */   private boolean _escaping = true;
/*      */ 
/*      */ 
/*      */   
/*      */   public SimpleResultTreeImpl(XSLTCDTMManager dtmManager, int documentID) {
/*  276 */     this._dtmManager = dtmManager;
/*  277 */     this._documentID = documentID;
/*  278 */     this._textArray = new String[4];
/*      */   }
/*      */ 
/*      */   
/*      */   public DTMManagerDefault getDTMManager() {
/*  283 */     return this._dtmManager;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDocument() {
/*  289 */     return this._documentID;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStringValue() {
/*  295 */     return this._text;
/*      */   }
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getIterator() {
/*  300 */     return new SingletonIterator(getDocument());
/*      */   }
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getChildren(int node) {
/*  305 */     return (new SimpleIterator()).setStartNode(node);
/*      */   }
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getTypedChildren(int type) {
/*  310 */     return new SimpleIterator(1, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getAxisIterator(int axis) {
/*  317 */     switch (axis) {
/*      */       
/*      */       case 3:
/*      */       case 4:
/*  321 */         return new SimpleIterator(1);
/*      */       case 0:
/*      */       case 10:
/*  324 */         return new SimpleIterator(0);
/*      */       case 1:
/*  326 */         return (new SimpleIterator(0)).includeSelf();
/*      */       case 5:
/*  328 */         return (new SimpleIterator(1)).includeSelf();
/*      */       case 13:
/*  330 */         return new SingletonIterator();
/*      */     } 
/*  332 */     return EMPTY_ITERATOR;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getTypedAxisIterator(int axis, int type) {
/*  338 */     switch (axis) {
/*      */       
/*      */       case 3:
/*      */       case 4:
/*  342 */         return new SimpleIterator(1, type);
/*      */       case 0:
/*      */       case 10:
/*  345 */         return new SimpleIterator(0, type);
/*      */       case 1:
/*  347 */         return (new SimpleIterator(0, type)).includeSelf();
/*      */       case 5:
/*  349 */         return (new SimpleIterator(1, type)).includeSelf();
/*      */       case 13:
/*  351 */         return new SingletonIterator(type);
/*      */     } 
/*  353 */     return EMPTY_ITERATOR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getNthDescendant(int node, int n, boolean includeself) {
/*  360 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns) {
/*  365 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iter, int returnType, String value, boolean op) {
/*  372 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node) {
/*  377 */     return source;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNodeName(int node) {
/*  382 */     if (getNodeIdent(node) == 1) {
/*  383 */       return "#text";
/*      */     }
/*  385 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNodeNameX(int node) {
/*  390 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespaceName(int node) {
/*  395 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getExpandedTypeID(int nodeHandle) {
/*  401 */     int nodeID = getNodeIdent(nodeHandle);
/*  402 */     if (nodeID == 1)
/*  403 */       return 3; 
/*  404 */     if (nodeID == 0) {
/*  405 */       return 0;
/*      */     }
/*  407 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNamespaceType(int node) {
/*  412 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getParent(int nodeHandle) {
/*  417 */     int nodeID = getNodeIdent(nodeHandle);
/*  418 */     return (nodeID == 1) ? getNodeHandle(0) : -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAttributeNode(int gType, int element) {
/*  423 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getStringValueX(int nodeHandle) {
/*  428 */     int nodeID = getNodeIdent(nodeHandle);
/*  429 */     if (nodeID == 0 || nodeID == 1) {
/*  430 */       return this._text;
/*      */     }
/*  432 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void copy(int node, SerializationHandler handler) throws TransletException {
/*  438 */     characters(node, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void copy(DTMAxisIterator nodes, SerializationHandler handler) throws TransletException {
/*      */     int node;
/*  445 */     while ((node = nodes.next()) != -1)
/*      */     {
/*  447 */       copy(node, handler);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String shallowCopy(int node, SerializationHandler handler) throws TransletException {
/*  454 */     characters(node, handler);
/*  455 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean lessThan(int node1, int node2) {
/*  460 */     if (node1 == -1) {
/*  461 */       return false;
/*      */     }
/*  463 */     if (node2 == -1) {
/*  464 */       return true;
/*      */     }
/*      */     
/*  467 */     return (node1 < node2);
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
/*      */   public void characters(int node, SerializationHandler handler) throws TransletException {
/*  479 */     int nodeID = getNodeIdent(node);
/*  480 */     if (nodeID == 0 || nodeID == 1) {
/*  481 */       boolean escapeBit = false;
/*  482 */       boolean oldEscapeSetting = false;
/*      */       
/*      */       try {
/*  485 */         for (int i = 0; i < this._size; i++) {
/*      */           
/*  487 */           if (this._dontEscape != null) {
/*  488 */             escapeBit = this._dontEscape.getBit(i);
/*  489 */             if (escapeBit) {
/*  490 */               oldEscapeSetting = handler.setEscaping(false);
/*      */             }
/*      */           } 
/*      */           
/*  494 */           handler.characters(this._textArray[i]);
/*      */           
/*  496 */           if (escapeBit) {
/*  497 */             handler.setEscaping(oldEscapeSetting);
/*      */           }
/*      */         } 
/*  500 */       } catch (SAXException e) {
/*  501 */         throw new TransletException(e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Node makeNode(int index) {
/*  509 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Node makeNode(DTMAxisIterator iter) {
/*  514 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public NodeList makeNodeList(int index) {
/*  519 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public NodeList makeNodeList(DTMAxisIterator iter) {
/*  524 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLanguage(int node) {
/*  529 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSize() {
/*  534 */     return 2;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDocumentURI(int node) {
/*  539 */     return "simple_rtf" + _documentURIIndex++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFilter(StripFilter filter) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupMapping(String[] names, String[] uris, int[] types, String[] namespaces) {}
/*      */ 
/*      */   
/*      */   public boolean isElement(int node) {
/*  552 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAttribute(int node) {
/*  557 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String lookupNamespace(int node, String prefix) throws TransletException {
/*  563 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNodeIdent(int nodehandle) {
/*  571 */     return (nodehandle != -1) ? (nodehandle - this._documentID) : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNodeHandle(int nodeId) {
/*  579 */     return (nodeId != -1) ? (nodeId + this._documentID) : -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public DOM getResultTreeFrag(int initialSize, int rtfType) {
/*  584 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public DOM getResultTreeFrag(int initialSize, int rtfType, boolean addToManager) {
/*  589 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public SerializationHandler getOutputDomBuilder() {
/*  594 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNSType(int node) {
/*  599 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUnparsedEntityURI(String name) {
/*  604 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<String, Integer> getElementsWithIDs() {
/*  609 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startDocument() throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endDocument() throws SAXException {
/*  628 */     if (this._size == 1) {
/*  629 */       this._text = this._textArray[0];
/*      */     } else {
/*  631 */       StringBuffer buffer = new StringBuffer();
/*  632 */       for (int i = 0; i < this._size; i++) {
/*  633 */         buffer.append(this._textArray[i]);
/*      */       }
/*  635 */       this._text = buffer.toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void characters(String str) throws SAXException {
/*  642 */     if (this._size >= this._textArray.length) {
/*  643 */       String[] newTextArray = new String[this._textArray.length * 2];
/*  644 */       System.arraycopy(this._textArray, 0, newTextArray, 0, this._textArray.length);
/*  645 */       this._textArray = newTextArray;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  650 */     if (!this._escaping) {
/*      */       
/*  652 */       if (this._dontEscape == null) {
/*  653 */         this._dontEscape = new BitArray(8);
/*      */       }
/*      */ 
/*      */       
/*  657 */       if (this._size >= this._dontEscape.size()) {
/*  658 */         this._dontEscape.resize(this._dontEscape.size() * 2);
/*      */       }
/*  660 */       this._dontEscape.setBit(this._size);
/*      */     } 
/*      */     
/*  663 */     this._textArray[this._size++] = str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void characters(char[] ch, int offset, int length) throws SAXException {
/*  669 */     if (this._size >= this._textArray.length) {
/*  670 */       String[] newTextArray = new String[this._textArray.length * 2];
/*  671 */       System.arraycopy(this._textArray, 0, newTextArray, 0, this._textArray.length);
/*  672 */       this._textArray = newTextArray;
/*      */     } 
/*      */     
/*  675 */     if (!this._escaping) {
/*  676 */       if (this._dontEscape == null) {
/*  677 */         this._dontEscape = new BitArray(8);
/*      */       }
/*      */       
/*  680 */       if (this._size >= this._dontEscape.size()) {
/*  681 */         this._dontEscape.resize(this._dontEscape.size() * 2);
/*      */       }
/*  683 */       this._dontEscape.setBit(this._size);
/*      */     } 
/*      */     
/*  686 */     this._textArray[this._size++] = new String(ch, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setEscaping(boolean escape) throws SAXException {
/*  692 */     boolean temp = this._escaping;
/*  693 */     this._escaping = escape;
/*  694 */     return temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String featureId, boolean state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String property, Object value) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMAxisTraverser getAxisTraverser(int axis) {
/*  718 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasChildNodes(int nodeHandle) {
/*  723 */     return (getNodeIdent(nodeHandle) == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getFirstChild(int nodeHandle) {
/*  728 */     int nodeID = getNodeIdent(nodeHandle);
/*  729 */     if (nodeID == 0) {
/*  730 */       return getNodeHandle(1);
/*      */     }
/*  732 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLastChild(int nodeHandle) {
/*  737 */     return getFirstChild(nodeHandle);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAttributeNode(int elementHandle, String namespaceURI, String name) {
/*  742 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getFirstAttribute(int nodeHandle) {
/*  747 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getFirstNamespaceNode(int nodeHandle, boolean inScope) {
/*  752 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNextSibling(int nodeHandle) {
/*  757 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getPreviousSibling(int nodeHandle) {
/*  762 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNextAttribute(int nodeHandle) {
/*  767 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextNamespaceNode(int baseHandle, int namespaceHandle, boolean inScope) {
/*  773 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getOwnerDocument(int nodeHandle) {
/*  778 */     return getDocument();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDocumentRoot(int nodeHandle) {
/*  783 */     return getDocument();
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLString getStringValue(int nodeHandle) {
/*  788 */     return new XMLStringDefault(getStringValueX(nodeHandle));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getStringValueChunkCount(int nodeHandle) {
/*  793 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] getStringValueChunk(int nodeHandle, int chunkIndex, int[] startAndLen) {
/*  799 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getExpandedTypeID(String namespace, String localName, int type) {
/*  804 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLocalNameFromExpandedNameID(int ExpandedNameID) {
/*  809 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespaceFromExpandedNameID(int ExpandedNameID) {
/*  814 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLocalName(int nodeHandle) {
/*  819 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPrefix(int nodeHandle) {
/*  824 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespaceURI(int nodeHandle) {
/*  829 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNodeValue(int nodeHandle) {
/*  834 */     return (getNodeIdent(nodeHandle) == 1) ? this._text : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public short getNodeType(int nodeHandle) {
/*  839 */     int nodeID = getNodeIdent(nodeHandle);
/*  840 */     if (nodeID == 1)
/*  841 */       return 3; 
/*  842 */     if (nodeID == 0) {
/*  843 */       return 0;
/*      */     }
/*  845 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getLevel(int nodeHandle) {
/*  851 */     int nodeID = getNodeIdent(nodeHandle);
/*  852 */     if (nodeID == 1)
/*  853 */       return 2; 
/*  854 */     if (nodeID == 0) {
/*  855 */       return 1;
/*      */     }
/*  857 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSupported(String feature, String version) {
/*  862 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDocumentBaseURI() {
/*  867 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentBaseURI(String baseURI) {}
/*      */ 
/*      */   
/*      */   public String getDocumentSystemIdentifier(int nodeHandle) {
/*  876 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDocumentEncoding(int nodeHandle) {
/*  881 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDocumentStandalone(int nodeHandle) {
/*  886 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDocumentVersion(int documentHandle) {
/*  891 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getDocumentAllDeclarationsProcessed() {
/*  896 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDocumentTypeDeclarationSystemIdentifier() {
/*  901 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDocumentTypeDeclarationPublicIdentifier() {
/*  906 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getElementById(String elementId) {
/*  911 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean supportsPreStripping() {
/*  916 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNodeAfter(int firstNodeHandle, int secondNodeHandle) {
/*  921 */     return lessThan(firstNodeHandle, secondNodeHandle);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCharacterElementContentWhitespace(int nodeHandle) {
/*  926 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDocumentAllDeclarationsProcessed(int documentHandle) {
/*  931 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAttributeSpecified(int attributeHandle) {
/*  936 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchToEvents(int nodeHandle, ContentHandler ch) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getNode(int nodeHandle) {
/*  954 */     return makeNode(nodeHandle);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean needsTwoThreads() {
/*  959 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public ContentHandler getContentHandler() {
/*  964 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public LexicalHandler getLexicalHandler() {
/*  969 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/*  974 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public DTDHandler getDTDHandler() {
/*  979 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public ErrorHandler getErrorHandler() {
/*  984 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public DeclHandler getDeclHandler() {
/*  989 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendChild(int newChild, boolean clone, boolean cloneDepth) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendTextChild(String str) {}
/*      */ 
/*      */   
/*      */   public SourceLocator getSourceLocatorFor(int node) {
/* 1002 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void documentRegistration() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void documentRelease() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void migrateTo(DTMManager manager) {}
/*      */ 
/*      */   
/*      */   public void release() {
/* 1019 */     if (this._documentID != 0) {
/* 1020 */       this._dtmManager.release(this, true);
/* 1021 */       this._documentID = 0;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/SimpleResultTreeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */