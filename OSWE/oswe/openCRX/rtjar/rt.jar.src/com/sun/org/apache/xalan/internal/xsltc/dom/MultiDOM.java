/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.StripFilter;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.TransletException;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
/*     */ import com.sun.org.apache.xml.internal.dtm.Axis;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIterNodeList;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBase;
/*     */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*     */ import com.sun.org.apache.xml.internal.utils.SuballocatedIntVector;
/*     */ import java.util.HashMap;
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
/*     */ public final class MultiDOM
/*     */   implements DOM
/*     */ {
/*     */   private static final int NO_TYPE = -2;
/*     */   private static final int INITIAL_SIZE = 4;
/*     */   private DOM[] _adapters;
/*     */   private DOMAdapter _main;
/*     */   private DTMManager _dtmManager;
/*     */   private int _free;
/*     */   private int _size;
/*  57 */   private Map<String, Integer> _documents = new HashMap<>();
/*     */   
/*     */   private final class AxisIterator
/*     */     extends DTMAxisIteratorBase
/*     */   {
/*     */     private final int _axis;
/*     */     private final int _type;
/*     */     private DTMAxisIterator _source;
/*  65 */     private int _dtmId = -1;
/*     */     
/*     */     public AxisIterator(int axis, int type) {
/*  68 */       this._axis = axis;
/*  69 */       this._type = type;
/*     */     }
/*     */     
/*     */     public int next() {
/*  73 */       if (this._source == null) {
/*  74 */         return -1;
/*     */       }
/*  76 */       return this._source.next();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setRestartable(boolean flag) {
/*  81 */       if (this._source != null) {
/*  82 */         this._source.setRestartable(flag);
/*     */       }
/*     */     }
/*     */     
/*     */     public DTMAxisIterator setStartNode(int node) {
/*  87 */       if (node == -1) {
/*  88 */         return this;
/*     */       }
/*     */       
/*  91 */       int dom = node >>> 16;
/*     */ 
/*     */       
/*  94 */       if (this._source == null || this._dtmId != dom) {
/*  95 */         if (this._type == -2) {
/*  96 */           this._source = MultiDOM.this._adapters[dom].getAxisIterator(this._axis);
/*  97 */         } else if (this._axis == 3) {
/*  98 */           this._source = MultiDOM.this._adapters[dom].getTypedChildren(this._type);
/*     */         } else {
/* 100 */           this._source = MultiDOM.this._adapters[dom].getTypedAxisIterator(this._axis, this._type);
/*     */         } 
/*     */       }
/*     */       
/* 104 */       this._dtmId = dom;
/* 105 */       this._source.setStartNode(node);
/* 106 */       return this;
/*     */     }
/*     */     
/*     */     public DTMAxisIterator reset() {
/* 110 */       if (this._source != null) {
/* 111 */         this._source.reset();
/*     */       }
/* 113 */       return this;
/*     */     }
/*     */     
/*     */     public int getLast() {
/* 117 */       if (this._source != null) {
/* 118 */         return this._source.getLast();
/*     */       }
/*     */       
/* 121 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPosition() {
/* 126 */       if (this._source != null) {
/* 127 */         return this._source.getPosition();
/*     */       }
/*     */       
/* 130 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isReverse() {
/* 135 */       return Axis.isReverse(this._axis);
/*     */     }
/*     */     
/*     */     public void setMark() {
/* 139 */       if (this._source != null) {
/* 140 */         this._source.setMark();
/*     */       }
/*     */     }
/*     */     
/*     */     public void gotoMark() {
/* 145 */       if (this._source != null) {
/* 146 */         this._source.gotoMark();
/*     */       }
/*     */     }
/*     */     
/*     */     public DTMAxisIterator cloneIterator() {
/* 151 */       AxisIterator clone = new AxisIterator(this._axis, this._type);
/* 152 */       if (this._source != null) {
/* 153 */         clone._source = this._source.cloneIterator();
/*     */       }
/* 155 */       clone._dtmId = this._dtmId;
/* 156 */       return clone;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class NodeValueIterator
/*     */     extends DTMAxisIteratorBase
/*     */   {
/*     */     private DTMAxisIterator _source;
/*     */     
/*     */     private String _value;
/*     */     
/*     */     private boolean _op;
/*     */     
/*     */     private final boolean _isReverse;
/* 171 */     private int _returnType = 1;
/*     */ 
/*     */     
/*     */     public NodeValueIterator(DTMAxisIterator source, int returnType, String value, boolean op) {
/* 175 */       this._source = source;
/* 176 */       this._returnType = returnType;
/* 177 */       this._value = value;
/* 178 */       this._op = op;
/* 179 */       this._isReverse = source.isReverse();
/*     */     }
/*     */     
/*     */     public boolean isReverse() {
/* 183 */       return this._isReverse;
/*     */     }
/*     */     
/*     */     public DTMAxisIterator cloneIterator() {
/*     */       try {
/* 188 */         NodeValueIterator clone = (NodeValueIterator)clone();
/* 189 */         clone._source = this._source.cloneIterator();
/* 190 */         clone.setRestartable(false);
/* 191 */         return clone.reset();
/*     */       }
/* 193 */       catch (CloneNotSupportedException e) {
/* 194 */         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e
/* 195 */             .toString());
/* 196 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void setRestartable(boolean isRestartable) {
/* 202 */       this._isRestartable = isRestartable;
/* 203 */       this._source.setRestartable(isRestartable);
/*     */     }
/*     */     
/*     */     public DTMAxisIterator reset() {
/* 207 */       this._source.reset();
/* 208 */       return resetPosition();
/*     */     }
/*     */ 
/*     */     
/*     */     public int next() {
/*     */       int node;
/* 214 */       while ((node = this._source.next()) != -1) {
/* 215 */         String val = MultiDOM.this.getStringValueX(node);
/* 216 */         if (this._value.equals(val) == this._op) {
/* 217 */           if (this._returnType == 0) {
/* 218 */             return returnNode(node);
/*     */           }
/* 220 */           return returnNode(MultiDOM.this.getParent(node));
/*     */         } 
/*     */       } 
/* 223 */       return -1;
/*     */     }
/*     */     
/*     */     public DTMAxisIterator setStartNode(int node) {
/* 227 */       if (this._isRestartable) {
/* 228 */         this._source.setStartNode(this._startNode = node);
/* 229 */         return resetPosition();
/*     */       } 
/* 231 */       return this;
/*     */     }
/*     */     
/*     */     public void setMark() {
/* 235 */       this._source.setMark();
/*     */     }
/*     */     
/*     */     public void gotoMark() {
/* 239 */       this._source.gotoMark();
/*     */     }
/*     */   }
/*     */   
/*     */   public MultiDOM(DOM main) {
/* 244 */     this._size = 4;
/* 245 */     this._free = 1;
/* 246 */     this._adapters = new DOM[4];
/* 247 */     DOMAdapter adapter = (DOMAdapter)main;
/* 248 */     this._adapters[0] = adapter;
/* 249 */     this._main = adapter;
/* 250 */     DOM dom = adapter.getDOMImpl();
/* 251 */     if (dom instanceof DTMDefaultBase) {
/* 252 */       this._dtmManager = ((DTMDefaultBase)dom).getManager();
/*     */     }
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
/* 269 */     addDOMAdapter(adapter, false);
/*     */   }
/*     */   
/*     */   public int nextMask() {
/* 273 */     return this._free;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupMapping(String[] names, String[] uris, int[] types, String[] namespaces) {}
/*     */ 
/*     */   
/*     */   public int addDOMAdapter(DOMAdapter adapter) {
/* 281 */     return addDOMAdapter(adapter, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private int addDOMAdapter(DOMAdapter adapter, boolean indexByURI) {
/* 286 */     DOM dom = adapter.getDOMImpl();
/*     */     
/* 288 */     int domNo = 1;
/* 289 */     int dtmSize = 1;
/* 290 */     SuballocatedIntVector dtmIds = null;
/* 291 */     if (dom instanceof DTMDefaultBase) {
/* 292 */       DTMDefaultBase dtmdb = (DTMDefaultBase)dom;
/* 293 */       dtmIds = dtmdb.getDTMIDs();
/* 294 */       dtmSize = dtmIds.size();
/* 295 */       domNo = dtmIds.elementAt(dtmSize - 1) >>> 16;
/*     */     }
/* 297 */     else if (dom instanceof SimpleResultTreeImpl) {
/* 298 */       SimpleResultTreeImpl simpleRTF = (SimpleResultTreeImpl)dom;
/* 299 */       domNo = simpleRTF.getDocument() >>> 16;
/*     */     } 
/*     */     
/* 302 */     if (domNo >= this._size) {
/* 303 */       int oldSize = this._size;
/*     */       do {
/* 305 */         this._size *= 2;
/* 306 */       } while (this._size <= domNo);
/*     */       
/* 308 */       DOMAdapter[] newArray = new DOMAdapter[this._size];
/* 309 */       System.arraycopy(this._adapters, 0, newArray, 0, oldSize);
/* 310 */       this._adapters = (DOM[])newArray;
/*     */     } 
/*     */     
/* 313 */     this._free = domNo + 1;
/*     */     
/* 315 */     if (dtmSize == 1) {
/* 316 */       this._adapters[domNo] = adapter;
/*     */     }
/* 318 */     else if (dtmIds != null) {
/* 319 */       int domPos = 0;
/* 320 */       for (int i = dtmSize - 1; i >= 0; i--) {
/* 321 */         domPos = dtmIds.elementAt(i) >>> 16;
/* 322 */         this._adapters[domPos] = adapter;
/*     */       } 
/* 324 */       domNo = domPos;
/*     */     } 
/*     */ 
/*     */     
/* 328 */     if (indexByURI) {
/* 329 */       String uri = adapter.getDocumentURI(0);
/* 330 */       this._documents.put(uri, Integer.valueOf(domNo));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     if (dom instanceof AdaptiveResultTreeImpl) {
/* 337 */       AdaptiveResultTreeImpl adaptiveRTF = (AdaptiveResultTreeImpl)dom;
/* 338 */       DOM nestedDom = adaptiveRTF.getNestedDOM();
/* 339 */       if (nestedDom != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 344 */         DOMAdapter newAdapter = new DOMAdapter(nestedDom, adapter.getNamesArray(), adapter.getUrisArray(), adapter.getTypesArray(), adapter.getNamespaceArray());
/* 345 */         addDOMAdapter(newAdapter);
/*     */       } 
/*     */     } 
/*     */     
/* 349 */     return domNo;
/*     */   }
/*     */   
/*     */   public int getDocumentMask(String uri) {
/* 353 */     Integer domIdx = this._documents.get(uri);
/* 354 */     if (domIdx == null) {
/* 355 */       return -1;
/*     */     }
/* 357 */     return domIdx.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public DOM getDOMAdapter(String uri) {
/* 362 */     Integer domIdx = this._documents.get(uri);
/* 363 */     if (domIdx == null) {
/* 364 */       return null;
/*     */     }
/* 366 */     return this._adapters[domIdx.intValue()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDocument() {
/* 372 */     return this._main.getDocument();
/*     */   }
/*     */   
/*     */   public DTMManager getDTMManager() {
/* 376 */     return this._dtmManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getIterator() {
/* 384 */     return this._main.getIterator();
/*     */   }
/*     */   
/*     */   public String getStringValue() {
/* 388 */     return this._main.getStringValue();
/*     */   }
/*     */   
/*     */   public DTMAxisIterator getChildren(int node) {
/* 392 */     return this._adapters[getDTMId(node)].getChildren(node);
/*     */   }
/*     */   
/*     */   public DTMAxisIterator getTypedChildren(int type) {
/* 396 */     return new AxisIterator(3, type);
/*     */   }
/*     */   
/*     */   public DTMAxisIterator getAxisIterator(int axis) {
/* 400 */     return new AxisIterator(axis, -2);
/*     */   }
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getTypedAxisIterator(int axis, int type) {
/* 405 */     return new AxisIterator(axis, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getNthDescendant(int node, int n, boolean includeself) {
/* 411 */     return this._adapters[getDTMId(node)].getNthDescendant(node, n, includeself);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iterator, int type, String value, boolean op) {
/* 418 */     return new NodeValueIterator(iterator, type, value, op);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns) {
/* 424 */     DTMAxisIterator iterator = this._main.getNamespaceAxisIterator(axis, ns);
/* 425 */     return iterator;
/*     */   }
/*     */   
/*     */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node) {
/* 429 */     return this._adapters[getDTMId(node)].orderNodes(source, node);
/*     */   }
/*     */   
/*     */   public int getExpandedTypeID(int node) {
/* 433 */     if (node != -1) {
/* 434 */       return this._adapters[node >>> 16].getExpandedTypeID(node);
/*     */     }
/*     */     
/* 437 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNamespaceType(int node) {
/* 442 */     return this._adapters[getDTMId(node)].getNamespaceType(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNSType(int node) {
/* 447 */     return this._adapters[getDTMId(node)].getNSType(node);
/*     */   }
/*     */   
/*     */   public int getParent(int node) {
/* 451 */     if (node == -1) {
/* 452 */       return -1;
/*     */     }
/* 454 */     return this._adapters[node >>> 16].getParent(node);
/*     */   }
/*     */   
/*     */   public int getAttributeNode(int type, int el) {
/* 458 */     if (el == -1) {
/* 459 */       return -1;
/*     */     }
/* 461 */     return this._adapters[el >>> 16].getAttributeNode(type, el);
/*     */   }
/*     */   
/*     */   public String getNodeName(int node) {
/* 465 */     if (node == -1) {
/* 466 */       return "";
/*     */     }
/* 468 */     return this._adapters[node >>> 16].getNodeName(node);
/*     */   }
/*     */   
/*     */   public String getNodeNameX(int node) {
/* 472 */     if (node == -1) {
/* 473 */       return "";
/*     */     }
/* 475 */     return this._adapters[node >>> 16].getNodeNameX(node);
/*     */   }
/*     */   
/*     */   public String getNamespaceName(int node) {
/* 479 */     if (node == -1) {
/* 480 */       return "";
/*     */     }
/* 482 */     return this._adapters[node >>> 16].getNamespaceName(node);
/*     */   }
/*     */   
/*     */   public String getStringValueX(int node) {
/* 486 */     if (node == -1) {
/* 487 */       return "";
/*     */     }
/* 489 */     return this._adapters[node >>> 16].getStringValueX(node);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void copy(int node, SerializationHandler handler) throws TransletException {
/* 495 */     if (node != -1) {
/* 496 */       this._adapters[node >>> 16].copy(node, handler);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void copy(DTMAxisIterator nodes, SerializationHandler handler) throws TransletException {
/*     */     int node;
/* 504 */     while ((node = nodes.next()) != -1) {
/* 505 */       this._adapters[node >>> 16].copy(node, handler);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String shallowCopy(int node, SerializationHandler handler) throws TransletException {
/* 513 */     if (node == -1) {
/* 514 */       return "";
/*     */     }
/* 516 */     return this._adapters[node >>> 16].shallowCopy(node, handler);
/*     */   }
/*     */   
/*     */   public boolean lessThan(int node1, int node2) {
/* 520 */     if (node1 == -1) {
/* 521 */       return true;
/*     */     }
/* 523 */     if (node2 == -1) {
/* 524 */       return false;
/*     */     }
/* 526 */     int dom1 = getDTMId(node1);
/* 527 */     int dom2 = getDTMId(node2);
/* 528 */     return (dom1 == dom2) ? this._adapters[dom1].lessThan(node1, node2) : ((dom1 < dom2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(int textNode, SerializationHandler handler) throws TransletException {
/* 535 */     if (textNode != -1) {
/* 536 */       this._adapters[textNode >>> 16].characters(textNode, handler);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setFilter(StripFilter filter) {
/* 541 */     for (int dom = 0; dom < this._free; dom++) {
/* 542 */       if (this._adapters[dom] != null) {
/* 543 */         this._adapters[dom].setFilter(filter);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Node makeNode(int index) {
/* 549 */     if (index == -1) {
/* 550 */       return null;
/*     */     }
/* 552 */     return this._adapters[getDTMId(index)].makeNode(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Node makeNode(DTMAxisIterator iter) {
/* 557 */     return this._main.makeNode(iter);
/*     */   }
/*     */   
/*     */   public NodeList makeNodeList(int index) {
/* 561 */     if (index == -1) {
/* 562 */       return null;
/*     */     }
/* 564 */     return this._adapters[getDTMId(index)].makeNodeList(index);
/*     */   }
/*     */   
/*     */   public NodeList makeNodeList(DTMAxisIterator iter) {
/* 568 */     int index = iter.next();
/* 569 */     if (index == -1) {
/* 570 */       return new DTMAxisIterNodeList(null, null);
/*     */     }
/* 572 */     iter.reset();
/* 573 */     return this._adapters[getDTMId(index)].makeNodeList(iter);
/*     */   }
/*     */   
/*     */   public String getLanguage(int node) {
/* 577 */     return this._adapters[getDTMId(node)].getLanguage(node);
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 581 */     int size = 0;
/* 582 */     for (int i = 0; i < this._size; i++) {
/* 583 */       size += this._adapters[i].getSize();
/*     */     }
/* 585 */     return size;
/*     */   }
/*     */   
/*     */   public String getDocumentURI(int node) {
/* 589 */     if (node == -1) {
/* 590 */       node = 0;
/*     */     }
/* 592 */     return this._adapters[node >>> 16].getDocumentURI(0);
/*     */   }
/*     */   
/*     */   public boolean isElement(int node) {
/* 596 */     if (node == -1) {
/* 597 */       return false;
/*     */     }
/* 599 */     return this._adapters[node >>> 16].isElement(node);
/*     */   }
/*     */   
/*     */   public boolean isAttribute(int node) {
/* 603 */     if (node == -1) {
/* 604 */       return false;
/*     */     }
/* 606 */     return this._adapters[node >>> 16].isAttribute(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDTMId(int nodeHandle) {
/* 611 */     if (nodeHandle == -1) {
/* 612 */       return 0;
/*     */     }
/* 614 */     int id = nodeHandle >>> 16;
/* 615 */     while (id >= 2 && this._adapters[id] == this._adapters[id - 1]) {
/* 616 */       id--;
/*     */     }
/* 618 */     return id;
/*     */   }
/*     */   
/*     */   public DOM getDTM(int nodeHandle) {
/* 622 */     return this._adapters[getDTMId(nodeHandle)];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNodeIdent(int nodeHandle) {
/* 627 */     return this._adapters[nodeHandle >>> 16].getNodeIdent(nodeHandle);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNodeHandle(int nodeId) {
/* 632 */     return this._main.getNodeHandle(nodeId);
/*     */   }
/*     */ 
/*     */   
/*     */   public DOM getResultTreeFrag(int initSize, int rtfType) {
/* 637 */     return this._main.getResultTreeFrag(initSize, rtfType);
/*     */   }
/*     */ 
/*     */   
/*     */   public DOM getResultTreeFrag(int initSize, int rtfType, boolean addToManager) {
/* 642 */     return this._main.getResultTreeFrag(initSize, rtfType, addToManager);
/*     */   }
/*     */ 
/*     */   
/*     */   public DOM getMain() {
/* 647 */     return this._main;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializationHandler getOutputDomBuilder() {
/* 655 */     return this._main.getOutputDomBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String lookupNamespace(int node, String prefix) throws TransletException {
/* 661 */     return this._main.lookupNamespace(node, prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUnparsedEntityURI(String entity) {
/* 666 */     return this._main.getUnparsedEntityURI(entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Integer> getElementsWithIDs() {
/* 671 */     return this._main.getElementsWithIDs();
/*     */   }
/*     */   
/*     */   public void release() {
/* 675 */     this._main.release();
/*     */   }
/*     */   
/*     */   private boolean isMatchingAdapterEntry(DOM entry, DOMAdapter adapter) {
/* 679 */     DOM dom = adapter.getDOMImpl();
/*     */     
/* 681 */     return (entry == adapter || (dom instanceof AdaptiveResultTreeImpl && entry instanceof DOMAdapter && ((AdaptiveResultTreeImpl)dom)
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 690 */       .getNestedDOM() == ((DOMAdapter)entry).getDOMImpl()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeDOMAdapter(DOMAdapter adapter) {
/* 695 */     this._documents.remove(adapter.getDocumentURI(0));
/* 696 */     DOM dom = adapter.getDOMImpl();
/*     */     
/* 698 */     if (dom instanceof DTMDefaultBase) {
/* 699 */       SuballocatedIntVector ids = ((DTMDefaultBase)dom).getDTMIDs();
/* 700 */       int idsSize = ids.size();
/* 701 */       for (int i = 0; i < idsSize; i++) {
/* 702 */         this._adapters[ids.elementAt(i) >>> 16] = null;
/*     */       }
/*     */     } else {
/* 705 */       int id = dom.getDocument() >>> 16;
/* 706 */       if (id > 0 && id < this._adapters.length && isMatchingAdapterEntry(this._adapters[id], adapter)) {
/* 707 */         this._adapters[id] = null;
/*     */       } else {
/* 709 */         boolean found = false;
/* 710 */         for (int i = 0; i < this._adapters.length; i++) {
/* 711 */           if (isMatchingAdapterEntry(this._adapters[id], adapter)) {
/* 712 */             this._adapters[i] = null;
/* 713 */             found = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/MultiDOM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */