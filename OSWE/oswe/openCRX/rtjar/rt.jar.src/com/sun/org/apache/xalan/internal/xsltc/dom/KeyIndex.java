/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOMEnhancedForDTM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class KeyIndex
/*     */   extends DTMAxisIteratorBase
/*     */ {
/*     */   private Map<String, IntegerArray> _index;
/*  57 */   private int _currentDocumentNode = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private Map<Integer, Map> _rootToIndexMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private IntegerArray _nodes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DOM _dom;
/*     */ 
/*     */ 
/*     */   
/*     */   private DOMEnhancedForDTM _enhancedDOM;
/*     */ 
/*     */ 
/*     */   
/*  81 */   private int _markedPosition = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRestartable(boolean flag) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String value, int node, int rootNode) {
/*  94 */     if (this._currentDocumentNode != rootNode) {
/*  95 */       this._currentDocumentNode = rootNode;
/*  96 */       this._index = new HashMap<>();
/*  97 */       this._rootToIndexMap.put(Integer.valueOf(rootNode), this._index);
/*     */     } 
/*     */     
/* 100 */     IntegerArray nodes = this._index.get(value);
/*     */     
/* 102 */     if (nodes == null) {
/* 103 */       nodes = new IntegerArray();
/* 104 */       this._index.put(value, nodes);
/* 105 */       nodes.add(node);
/*     */ 
/*     */     
/*     */     }
/* 109 */     else if (node != nodes.at(nodes.cardinality() - 1)) {
/* 110 */       nodes.add(node);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void merge(KeyIndex other) {
/* 119 */     if (other == null)
/*     */       return; 
/* 121 */     if (other._nodes != null) {
/* 122 */       if (this._nodes == null) {
/* 123 */         this._nodes = (IntegerArray)other._nodes.clone();
/*     */       } else {
/*     */         
/* 126 */         this._nodes.merge(other._nodes);
/*     */       } 
/*     */     }
/*     */   }
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
/*     */   public void lookupId(Object value) {
/* 141 */     this._nodes = null;
/*     */     
/* 143 */     StringTokenizer values = new StringTokenizer((String)value, " \n\t");
/*     */     
/* 145 */     while (values.hasMoreElements()) {
/* 146 */       String token = (String)values.nextElement();
/* 147 */       IntegerArray nodes = this._index.get(token);
/*     */       
/* 149 */       if (nodes == null && this._enhancedDOM != null && this._enhancedDOM
/* 150 */         .hasDOMSource()) {
/* 151 */         nodes = getDOMNodeById(token);
/*     */       }
/*     */       
/* 154 */       if (nodes == null)
/*     */         continue; 
/* 156 */       if (this._nodes == null) {
/* 157 */         nodes = (IntegerArray)nodes.clone();
/* 158 */         this._nodes = nodes;
/*     */         continue;
/*     */       } 
/* 161 */       this._nodes.merge(nodes);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntegerArray getDOMNodeById(String id) {
/* 173 */     IntegerArray nodes = null;
/*     */     
/* 175 */     if (this._enhancedDOM != null) {
/* 176 */       int ident = this._enhancedDOM.getElementById(id);
/*     */       
/* 178 */       if (ident != -1) {
/* 179 */         Integer root = new Integer(this._enhancedDOM.getDocument());
/* 180 */         Map<String, IntegerArray> index = this._rootToIndexMap.get(root);
/*     */         
/* 182 */         if (index == null) {
/* 183 */           index = new HashMap<>();
/* 184 */           this._rootToIndexMap.put(root, index);
/*     */         } else {
/* 186 */           nodes = index.get(id);
/*     */         } 
/*     */         
/* 189 */         if (nodes == null) {
/* 190 */           nodes = new IntegerArray();
/* 191 */           index.put(id, nodes);
/*     */         } 
/*     */         
/* 194 */         nodes.add(this._enhancedDOM.getNodeHandle(ident));
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     return nodes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void lookupKey(Object value) {
/* 209 */     IntegerArray nodes = this._index.get(value);
/* 210 */     this._nodes = (nodes != null) ? (IntegerArray)nodes.clone() : null;
/* 211 */     this._position = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int next() {
/* 221 */     if (this._nodes == null) return -1;
/*     */     
/* 223 */     return (this._position < this._nodes.cardinality()) ? this._dom
/* 224 */       .getNodeHandle(this._nodes.at(this._position++)) : -1;
/*     */   }
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
/*     */   public int containsID(int node, Object value) {
/* 240 */     String string = (String)value;
/*     */     
/* 242 */     int rootHandle = this._dom.getAxisIterator(19).setStartNode(node).next();
/*     */ 
/*     */ 
/*     */     
/* 246 */     Map<String, IntegerArray> index = this._rootToIndexMap.get(Integer.valueOf(rootHandle));
/*     */ 
/*     */     
/* 249 */     StringTokenizer values = new StringTokenizer(string, " \n\t");
/*     */     
/* 251 */     while (values.hasMoreElements()) {
/* 252 */       String token = (String)values.nextElement();
/* 253 */       IntegerArray nodes = null;
/*     */       
/* 255 */       if (index != null) {
/* 256 */         nodes = index.get(token);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 261 */       if (nodes == null && this._enhancedDOM != null && this._enhancedDOM
/* 262 */         .hasDOMSource()) {
/* 263 */         nodes = getDOMNodeById(token);
/*     */       }
/*     */ 
/*     */       
/* 267 */       if (nodes != null && nodes.indexOf(node) >= 0) {
/* 268 */         return 1;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 273 */     return 0;
/*     */   }
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
/*     */   public int containsKey(int node, Object value) {
/* 293 */     int rootHandle = this._dom.getAxisIterator(19).setStartNode(node).next();
/*     */ 
/*     */ 
/*     */     
/* 297 */     Map<String, IntegerArray> index = this._rootToIndexMap.get(new Integer(rootHandle));
/*     */ 
/*     */ 
/*     */     
/* 301 */     if (index != null) {
/* 302 */       IntegerArray nodes = index.get(value);
/* 303 */       return (nodes != null && nodes.indexOf(node) >= 0) ? 1 : 0;
/*     */     } 
/*     */ 
/*     */     
/* 307 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator reset() {
/* 317 */     this._position = 0;
/* 318 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLast() {
/* 328 */     return (this._nodes == null) ? 0 : this._nodes.cardinality();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 338 */     return this._position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMark() {
/* 348 */     this._markedPosition = this._position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void gotoMark() {
/* 358 */     this._position = this._markedPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator setStartNode(int start) {
/* 369 */     if (start == -1) {
/* 370 */       this._nodes = null;
/*     */     }
/* 372 */     else if (this._nodes != null) {
/* 373 */       this._position = 0;
/*     */     } 
/* 375 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStartNode() {
/* 386 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReverse() {
/* 396 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTMAxisIterator cloneIterator() {
/* 406 */     KeyIndex other = new KeyIndex(0);
/* 407 */     other._index = this._index;
/* 408 */     other._rootToIndexMap = this._rootToIndexMap;
/* 409 */     other._nodes = this._nodes;
/* 410 */     other._position = this._position;
/* 411 */     return other;
/*     */   }
/*     */   
/*     */   public void setDom(DOM dom, int node) {
/* 415 */     this._dom = dom;
/*     */ 
/*     */ 
/*     */     
/* 419 */     if (dom instanceof MultiDOM) {
/* 420 */       dom = ((MultiDOM)dom).getDTM(node);
/*     */     }
/*     */     
/* 423 */     if (dom instanceof DOMEnhancedForDTM) {
/* 424 */       this._enhancedDOM = (DOMEnhancedForDTM)dom;
/*     */     }
/* 426 */     else if (dom instanceof DOMAdapter) {
/* 427 */       DOM idom = ((DOMAdapter)dom).getDOMImpl();
/* 428 */       if (idom instanceof DOMEnhancedForDTM) {
/* 429 */         this._enhancedDOM = (DOMEnhancedForDTM)idom;
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   public KeyIndexIterator getKeyIndexIterator(Object keyValue, boolean isKeyCall) {
/* 447 */     if (keyValue instanceof DTMAxisIterator) {
/* 448 */       return getKeyIndexIterator((DTMAxisIterator)keyValue, isKeyCall);
/*     */     }
/* 450 */     return getKeyIndexIterator(BasisLibrary.stringF(keyValue, this._dom), isKeyCall);
/*     */   }
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
/*     */   public KeyIndexIterator getKeyIndexIterator(String keyValue, boolean isKeyCall) {
/* 468 */     return new KeyIndexIterator(keyValue, isKeyCall);
/*     */   }
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
/*     */   public KeyIndexIterator getKeyIndexIterator(DTMAxisIterator keyValue, boolean isKeyCall) {
/* 484 */     return new KeyIndexIterator(keyValue, isKeyCall);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 490 */   private static final IntegerArray EMPTY_NODES = new IntegerArray(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyIndex(int dummy) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class KeyIndexIterator
/*     */     extends MultiValuedNodeHeapIterator
/*     */   {
/*     */     private IntegerArray _nodes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private DTMAxisIterator _keyValueIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String _keyValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean _isKeyIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected class KeyIndexHeapNode
/*     */       extends MultiValuedNodeHeapIterator.HeapNode
/*     */     {
/*     */       private IntegerArray _nodes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 552 */       private int _position = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 558 */       private int _markPosition = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       KeyIndexHeapNode(IntegerArray nodes) {
/* 566 */         this._nodes = nodes;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public int step() {
/* 575 */         if (this._position < this._nodes.cardinality()) {
/* 576 */           this._node = this._nodes.at(this._position);
/* 577 */           this._position++;
/*     */         } else {
/* 579 */           this._node = -1;
/*     */         } 
/*     */         
/* 582 */         return this._node;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public MultiValuedNodeHeapIterator.HeapNode cloneHeapNode() {
/* 593 */         KeyIndexHeapNode clone = (KeyIndexHeapNode)super.cloneHeapNode();
/*     */         
/* 595 */         clone._nodes = this._nodes;
/* 596 */         clone._position = this._position;
/* 597 */         clone._markPosition = this._markPosition;
/*     */         
/* 599 */         return clone;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void setMark() {
/* 607 */         this._markPosition = this._position;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void gotoMark() {
/* 614 */         this._position = this._markPosition;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean isLessThan(MultiValuedNodeHeapIterator.HeapNode heapNode) {
/* 626 */         return (this._node < heapNode._node);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public MultiValuedNodeHeapIterator.HeapNode setStartNode(int node) {
/* 638 */         return this;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public MultiValuedNodeHeapIterator.HeapNode reset() {
/* 645 */         this._position = 0;
/* 646 */         return this;
/*     */       }
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
/*     */     KeyIndexIterator(String keyValue, boolean isKeyIterator) {
/* 661 */       this._isKeyIterator = isKeyIterator;
/* 662 */       this._keyValue = keyValue;
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
/*     */     KeyIndexIterator(DTMAxisIterator keyValues, boolean isKeyIterator) {
/* 675 */       this._keyValueIterator = keyValues;
/* 676 */       this._isKeyIterator = isKeyIterator;
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
/*     */     protected IntegerArray lookupNodes(int root, String keyValue) {
/* 688 */       IntegerArray result = null;
/*     */ 
/*     */       
/* 691 */       Map<String, IntegerArray> index = (Map<String, IntegerArray>)KeyIndex.this._rootToIndexMap.get(Integer.valueOf(root));
/*     */       
/* 693 */       if (!this._isKeyIterator) {
/*     */ 
/*     */         
/* 696 */         StringTokenizer values = new StringTokenizer(keyValue, " \n\t");
/*     */ 
/*     */         
/* 699 */         while (values.hasMoreElements()) {
/* 700 */           String token = (String)values.nextElement();
/* 701 */           IntegerArray nodes = null;
/*     */ 
/*     */           
/* 704 */           if (index != null) {
/* 705 */             nodes = index.get(token);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 710 */           if (nodes == null && KeyIndex.this._enhancedDOM != null && KeyIndex.this
/* 711 */             ._enhancedDOM.hasDOMSource()) {
/* 712 */             nodes = KeyIndex.this.getDOMNodeById(token);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 717 */           if (nodes != null) {
/* 718 */             if (result == null) {
/* 719 */               result = (IntegerArray)nodes.clone(); continue;
/*     */             } 
/* 721 */             result.merge(nodes);
/*     */           }
/*     */         
/*     */         } 
/* 725 */       } else if (index != null) {
/*     */         
/* 727 */         result = index.get(keyValue);
/*     */       } 
/*     */       
/* 730 */       return result;
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
/*     */     public DTMAxisIterator setStartNode(int node) {
/* 742 */       this._startNode = node;
/*     */ 
/*     */ 
/*     */       
/* 746 */       if (this._keyValueIterator != null) {
/* 747 */         this._keyValueIterator = this._keyValueIterator.setStartNode(node);
/*     */       }
/*     */       
/* 750 */       init();
/*     */       
/* 752 */       return super.setStartNode(node);
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
/*     */     public int next() {
/*     */       int nodeHandle;
/* 768 */       if (this._nodes != null) {
/* 769 */         if (this._position < this._nodes.cardinality()) {
/* 770 */           nodeHandle = returnNode(this._nodes.at(this._position));
/*     */         } else {
/* 772 */           nodeHandle = -1;
/*     */         } 
/*     */       } else {
/* 775 */         nodeHandle = super.next();
/*     */       } 
/*     */       
/* 778 */       return nodeHandle;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DTMAxisIterator reset() {
/* 788 */       if (this._nodes == null) {
/* 789 */         init();
/*     */       } else {
/* 791 */         super.reset();
/*     */       } 
/*     */       
/* 794 */       return resetPosition();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void init() {
/* 804 */       super.init();
/* 805 */       this._position = 0;
/*     */ 
/*     */ 
/*     */       
/* 809 */       int rootHandle = KeyIndex.this._dom.getAxisIterator(19).setStartNode(this._startNode).next();
/*     */ 
/*     */       
/* 812 */       if (this._keyValueIterator == null) {
/*     */         
/* 814 */         this._nodes = lookupNodes(rootHandle, this._keyValue);
/*     */         
/* 816 */         if (this._nodes == null) {
/* 817 */           this._nodes = KeyIndex.EMPTY_NODES;
/*     */         }
/*     */       } else {
/* 820 */         DTMAxisIterator keyValues = this._keyValueIterator.reset();
/* 821 */         int retrievedKeyValueIdx = 0;
/* 822 */         boolean foundNodes = false;
/*     */         
/* 824 */         this._nodes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 831 */         int keyValueNode = keyValues.next();
/* 832 */         for (; keyValueNode != -1; 
/* 833 */           keyValueNode = keyValues.next()) {
/*     */           
/* 835 */           String keyValue = BasisLibrary.stringF(keyValueNode, KeyIndex.this._dom);
/*     */           
/* 837 */           IntegerArray nodes = lookupNodes(rootHandle, keyValue);
/*     */           
/* 839 */           if (nodes != null) {
/* 840 */             if (!foundNodes) {
/* 841 */               this._nodes = nodes;
/* 842 */               foundNodes = true;
/*     */             } else {
/* 844 */               if (this._nodes != null) {
/* 845 */                 addHeapNode(new KeyIndexHeapNode(this._nodes));
/* 846 */                 this._nodes = null;
/*     */               } 
/* 848 */               addHeapNode(new KeyIndexHeapNode(nodes));
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 853 */         if (!foundNodes) {
/* 854 */           this._nodes = KeyIndex.EMPTY_NODES;
/*     */         }
/*     */       } 
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
/*     */     public int getLast() {
/* 868 */       return (this._nodes != null) ? this._nodes.cardinality() : super.getLast();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getNodeByPosition(int position) {
/* 878 */       int node = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 884 */       if (this._nodes != null) {
/* 885 */         if (position > 0) {
/* 886 */           if (position <= this._nodes.cardinality()) {
/* 887 */             this._position = position;
/* 888 */             node = this._nodes.at(position - 1);
/*     */           } else {
/* 890 */             this._position = this._nodes.cardinality();
/*     */           } 
/*     */         }
/*     */       } else {
/* 894 */         node = super.getNodeByPosition(position);
/*     */       } 
/*     */       
/* 897 */       return node;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/KeyIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */