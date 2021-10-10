/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOMEnhancedForDTM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.StripFilter;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.TransletException;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*     */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public final class DOMAdapter
/*     */   implements DOM
/*     */ {
/*     */   private DOMEnhancedForDTM _enhancedDOM;
/*     */   private DOM _dom;
/*     */   private String[] _namesArray;
/*     */   private String[] _urisArray;
/*     */   private int[] _typesArray;
/*     */   private String[] _namespaceArray;
/*  51 */   private short[] _mapping = null;
/*  52 */   private int[] _reverse = null;
/*  53 */   private short[] _NSmapping = null;
/*  54 */   private short[] _NSreverse = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private int _multiDOMMask;
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMAdapter(DOM dom, String[] namesArray, String[] urisArray, int[] typesArray, String[] namespaceArray) {
/*  63 */     if (dom instanceof DOMEnhancedForDTM) {
/*  64 */       this._enhancedDOM = (DOMEnhancedForDTM)dom;
/*     */     }
/*     */     
/*  67 */     this._dom = dom;
/*  68 */     this._namesArray = namesArray;
/*  69 */     this._urisArray = urisArray;
/*  70 */     this._typesArray = typesArray;
/*  71 */     this._namespaceArray = namespaceArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupMapping(String[] names, String[] urisArray, int[] typesArray, String[] namespaces) {
/*  76 */     this._namesArray = names;
/*  77 */     this._urisArray = urisArray;
/*  78 */     this._typesArray = typesArray;
/*  79 */     this._namespaceArray = namespaces;
/*     */   }
/*     */   
/*     */   public String[] getNamesArray() {
/*  83 */     return this._namesArray;
/*     */   }
/*     */   
/*     */   public String[] getUrisArray() {
/*  87 */     return this._urisArray;
/*     */   }
/*     */   
/*     */   public int[] getTypesArray() {
/*  91 */     return this._typesArray;
/*     */   }
/*     */   
/*     */   public String[] getNamespaceArray() {
/*  95 */     return this._namespaceArray;
/*     */   }
/*     */   
/*     */   public DOM getDOMImpl() {
/*  99 */     return this._dom;
/*     */   }
/*     */   
/*     */   private short[] getMapping() {
/* 103 */     if (this._mapping == null && 
/* 104 */       this._enhancedDOM != null) {
/* 105 */       this._mapping = this._enhancedDOM.getMapping(this._namesArray, this._urisArray, this._typesArray);
/*     */     }
/*     */ 
/*     */     
/* 109 */     return this._mapping;
/*     */   }
/*     */   
/*     */   private int[] getReverse() {
/* 113 */     if (this._reverse == null && 
/* 114 */       this._enhancedDOM != null) {
/* 115 */       this._reverse = this._enhancedDOM.getReverseMapping(this._namesArray, this._urisArray, this._typesArray);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 120 */     return this._reverse;
/*     */   }
/*     */   
/*     */   private short[] getNSMapping() {
/* 124 */     if (this._NSmapping == null && 
/* 125 */       this._enhancedDOM != null) {
/* 126 */       this._NSmapping = this._enhancedDOM.getNamespaceMapping(this._namespaceArray);
/*     */     }
/*     */     
/* 129 */     return this._NSmapping;
/*     */   }
/*     */   
/*     */   private short[] getNSReverse() {
/* 133 */     if (this._NSreverse == null && 
/* 134 */       this._enhancedDOM != null) {
/* 135 */       this
/* 136 */         ._NSreverse = this._enhancedDOM.getReverseNamespaceMapping(this._namespaceArray);
/*     */     }
/*     */     
/* 139 */     return this._NSreverse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getIterator() {
/* 146 */     return this._dom.getIterator();
/*     */   }
/*     */   
/*     */   public String getStringValue() {
/* 150 */     return this._dom.getStringValue();
/*     */   }
/*     */   
/*     */   public DTMAxisIterator getChildren(int node) {
/* 154 */     if (this._enhancedDOM != null) {
/* 155 */       return this._enhancedDOM.getChildren(node);
/*     */     }
/*     */     
/* 158 */     DTMAxisIterator iterator = this._dom.getChildren(node);
/* 159 */     return iterator.setStartNode(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFilter(StripFilter filter) {}
/*     */   
/*     */   public DTMAxisIterator getTypedChildren(int type) {
/* 166 */     int[] reverse = getReverse();
/*     */     
/* 168 */     if (this._enhancedDOM != null) {
/* 169 */       return this._enhancedDOM.getTypedChildren(reverse[type]);
/*     */     }
/*     */     
/* 172 */     return this._dom.getTypedChildren(type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns) {
/* 178 */     return this._dom.getNamespaceAxisIterator(axis, getNSReverse()[ns]);
/*     */   }
/*     */   
/*     */   public DTMAxisIterator getAxisIterator(int axis) {
/* 182 */     if (this._enhancedDOM != null) {
/* 183 */       return this._enhancedDOM.getAxisIterator(axis);
/*     */     }
/*     */     
/* 186 */     return this._dom.getAxisIterator(axis);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getTypedAxisIterator(int axis, int type) {
/* 192 */     int[] reverse = getReverse();
/* 193 */     if (this._enhancedDOM != null) {
/* 194 */       return this._enhancedDOM.getTypedAxisIterator(axis, reverse[type]);
/*     */     }
/* 196 */     return this._dom.getTypedAxisIterator(axis, type);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMultiDOMMask() {
/* 201 */     return this._multiDOMMask;
/*     */   }
/*     */   
/*     */   public void setMultiDOMMask(int mask) {
/* 205 */     this._multiDOMMask = mask;
/*     */   }
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getNthDescendant(int type, int n, boolean includeself) {
/* 210 */     return this._dom.getNthDescendant(getReverse()[type], n, includeself);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iterator, int type, String value, boolean op) {
/* 216 */     return this._dom.getNodeValueIterator(iterator, type, value, op);
/*     */   }
/*     */   
/*     */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node) {
/* 220 */     return this._dom.orderNodes(source, node);
/*     */   }
/*     */   public int getExpandedTypeID(int node) {
/*     */     int type;
/* 224 */     short[] mapping = getMapping();
/*     */     
/* 226 */     if (this._enhancedDOM != null) {
/* 227 */       type = mapping[this._enhancedDOM.getExpandedTypeID2(node)];
/*     */     
/*     */     }
/* 230 */     else if (null != mapping) {
/*     */       
/* 232 */       type = mapping[this._dom.getExpandedTypeID(node)];
/*     */     }
/*     */     else {
/*     */       
/* 236 */       type = this._dom.getExpandedTypeID(node);
/*     */     } 
/*     */     
/* 239 */     return type;
/*     */   }
/*     */   
/*     */   public int getNamespaceType(int node) {
/* 243 */     return getNSMapping()[this._dom.getNSType(node)];
/*     */   }
/*     */   
/*     */   public int getNSType(int node) {
/* 247 */     return this._dom.getNSType(node);
/*     */   }
/*     */   
/*     */   public int getParent(int node) {
/* 251 */     return this._dom.getParent(node);
/*     */   }
/*     */   
/*     */   public int getAttributeNode(int type, int element) {
/* 255 */     return this._dom.getAttributeNode(getReverse()[type], element);
/*     */   }
/*     */   
/*     */   public String getNodeName(int node) {
/* 259 */     if (node == -1) {
/* 260 */       return "";
/*     */     }
/* 262 */     return this._dom.getNodeName(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNodeNameX(int node) {
/* 267 */     if (node == -1) {
/* 268 */       return "";
/*     */     }
/* 270 */     return this._dom.getNodeNameX(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceName(int node) {
/* 275 */     if (node == -1) {
/* 276 */       return "";
/*     */     }
/* 278 */     return this._dom.getNamespaceName(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStringValueX(int node) {
/* 283 */     if (this._enhancedDOM != null) {
/* 284 */       return this._enhancedDOM.getStringValueX(node);
/*     */     }
/*     */     
/* 287 */     if (node == -1) {
/* 288 */       return "";
/*     */     }
/* 290 */     return this._dom.getStringValueX(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copy(int node, SerializationHandler handler) throws TransletException {
/* 297 */     this._dom.copy(node, handler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void copy(DTMAxisIterator nodes, SerializationHandler handler) throws TransletException {
/* 303 */     this._dom.copy(nodes, handler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String shallowCopy(int node, SerializationHandler handler) throws TransletException {
/* 309 */     if (this._enhancedDOM != null) {
/* 310 */       return this._enhancedDOM.shallowCopy(node, handler);
/*     */     }
/*     */     
/* 313 */     return this._dom.shallowCopy(node, handler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lessThan(int node1, int node2) {
/* 319 */     return this._dom.lessThan(node1, node2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(int textNode, SerializationHandler handler) throws TransletException {
/* 325 */     if (this._enhancedDOM != null) {
/* 326 */       this._enhancedDOM.characters(textNode, handler);
/*     */     } else {
/*     */       
/* 329 */       this._dom.characters(textNode, handler);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Node makeNode(int index) {
/* 335 */     return this._dom.makeNode(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Node makeNode(DTMAxisIterator iter) {
/* 340 */     return this._dom.makeNode(iter);
/*     */   }
/*     */ 
/*     */   
/*     */   public NodeList makeNodeList(int index) {
/* 345 */     return this._dom.makeNodeList(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public NodeList makeNodeList(DTMAxisIterator iter) {
/* 350 */     return this._dom.makeNodeList(iter);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLanguage(int node) {
/* 355 */     return this._dom.getLanguage(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 360 */     return this._dom.getSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDocumentURI(String uri) {
/* 365 */     if (this._enhancedDOM != null) {
/* 366 */       this._enhancedDOM.setDocumentURI(uri);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDocumentURI() {
/* 372 */     if (this._enhancedDOM != null) {
/* 373 */       return this._enhancedDOM.getDocumentURI();
/*     */     }
/*     */     
/* 376 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDocumentURI(int node) {
/* 382 */     return this._dom.getDocumentURI(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDocument() {
/* 387 */     return this._dom.getDocument();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isElement(int node) {
/* 392 */     return this._dom.isElement(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttribute(int node) {
/* 397 */     return this._dom.isAttribute(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNodeIdent(int nodeHandle) {
/* 402 */     return this._dom.getNodeIdent(nodeHandle);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNodeHandle(int nodeId) {
/* 407 */     return this._dom.getNodeHandle(nodeId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOM getResultTreeFrag(int initSize, int rtfType) {
/* 415 */     if (this._enhancedDOM != null) {
/* 416 */       return this._enhancedDOM.getResultTreeFrag(initSize, rtfType);
/*     */     }
/*     */     
/* 419 */     return this._dom.getResultTreeFrag(initSize, rtfType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOM getResultTreeFrag(int initSize, int rtfType, boolean addToManager) {
/* 429 */     if (this._enhancedDOM != null) {
/* 430 */       return this._enhancedDOM.getResultTreeFrag(initSize, rtfType, addToManager);
/*     */     }
/*     */ 
/*     */     
/* 434 */     return this._dom.getResultTreeFrag(initSize, rtfType, addToManager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializationHandler getOutputDomBuilder() {
/* 444 */     return this._dom.getOutputDomBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String lookupNamespace(int node, String prefix) throws TransletException {
/* 450 */     return this._dom.lookupNamespace(node, prefix);
/*     */   }
/*     */   
/*     */   public String getUnparsedEntityURI(String entity) {
/* 454 */     return this._dom.getUnparsedEntityURI(entity);
/*     */   }
/*     */   
/*     */   public Map<String, Integer> getElementsWithIDs() {
/* 458 */     return this._dom.getElementsWithIDs();
/*     */   }
/*     */   
/*     */   public void release() {
/* 462 */     this._dom.release();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/DOMAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */