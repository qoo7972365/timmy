/*      */ package com.sun.org.apache.xerces.internal.dom;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.dom.events.EventImpl;
/*      */ import com.sun.org.apache.xerces.internal.dom.events.MutationEventImpl;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.DOMImplementation;
/*      */ import org.w3c.dom.DocumentType;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.events.DocumentEvent;
/*      */ import org.w3c.dom.events.Event;
/*      */ import org.w3c.dom.events.EventException;
/*      */ import org.w3c.dom.events.EventListener;
/*      */ import org.w3c.dom.events.MutationEvent;
/*      */ import org.w3c.dom.ranges.DocumentRange;
/*      */ import org.w3c.dom.ranges.Range;
/*      */ import org.w3c.dom.traversal.DocumentTraversal;
/*      */ import org.w3c.dom.traversal.NodeFilter;
/*      */ import org.w3c.dom.traversal.NodeIterator;
/*      */ import org.w3c.dom.traversal.TreeWalker;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DocumentImpl
/*      */   extends CoreDocumentImpl
/*      */   implements DocumentTraversal, DocumentEvent, DocumentRange
/*      */ {
/*      */   static final long serialVersionUID = 515687835542616694L;
/*      */   protected List<NodeIterator> iterators;
/*      */   protected List<Range> ranges;
/*      */   protected Map<NodeImpl, List<LEntry>> eventListeners;
/*      */   protected boolean mutationEvents = false;
/*  125 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("iterators", Vector.class), new ObjectStreamField("ranges", Vector.class), new ObjectStreamField("eventListeners", Hashtable.class), new ObjectStreamField("mutationEvents", boolean.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   EnclosingAttr savedEnclosingAttr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DocumentImpl() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DocumentImpl(boolean grammarAccess) {
/*  147 */     super(grammarAccess);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DocumentImpl(DocumentType doctype) {
/*  156 */     super(doctype);
/*      */   }
/*      */ 
/*      */   
/*      */   public DocumentImpl(DocumentType doctype, boolean grammarAccess) {
/*  161 */     super(doctype, grammarAccess);
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
/*      */   public Node cloneNode(boolean deep) {
/*  179 */     DocumentImpl newdoc = new DocumentImpl();
/*  180 */     callUserDataHandlers(this, newdoc, (short)1);
/*  181 */     cloneNode(newdoc, deep);
/*      */ 
/*      */     
/*  184 */     newdoc.mutationEvents = this.mutationEvents;
/*      */     
/*  186 */     return newdoc;
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
/*      */   public DOMImplementation getImplementation() {
/*  199 */     return DOMImplementationImpl.getDOMImplementation();
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
/*      */   public NodeIterator createNodeIterator(Node root, short whatToShow, NodeFilter filter) {
/*  220 */     return createNodeIterator(root, whatToShow, filter, true);
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
/*      */   public NodeIterator createNodeIterator(Node root, int whatToShow, NodeFilter filter, boolean entityReferenceExpansion) {
/*  241 */     if (root == null) {
/*  242 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/*  243 */       throw new DOMException((short)9, msg);
/*      */     } 
/*      */     
/*  246 */     NodeIterator iterator = new NodeIteratorImpl(this, root, whatToShow, filter, entityReferenceExpansion);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  251 */     if (this.iterators == null) {
/*  252 */       this.iterators = new ArrayList<>();
/*      */     }
/*      */     
/*  255 */     this.iterators.add(iterator);
/*      */     
/*  257 */     return iterator;
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
/*      */   public TreeWalker createTreeWalker(Node root, short whatToShow, NodeFilter filter) {
/*  272 */     return createTreeWalker(root, whatToShow, filter, true);
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
/*      */   public TreeWalker createTreeWalker(Node root, int whatToShow, NodeFilter filter, boolean entityReferenceExpansion) {
/*  289 */     if (root == null) {
/*  290 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/*  291 */       throw new DOMException((short)9, msg);
/*      */     } 
/*  293 */     return new TreeWalkerImpl(root, whatToShow, filter, entityReferenceExpansion);
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
/*      */   void removeNodeIterator(NodeIterator nodeIterator) {
/*  309 */     if (nodeIterator == null)
/*  310 */       return;  if (this.iterators == null)
/*      */       return; 
/*  312 */     this.iterators.remove(nodeIterator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Range createRange() {
/*  322 */     if (this.ranges == null) {
/*  323 */       this.ranges = new ArrayList<>();
/*      */     }
/*      */     
/*  326 */     Range range = new RangeImpl(this);
/*  327 */     this.ranges.add(range);
/*      */     
/*  329 */     return range;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removeRange(Range range) {
/*  339 */     if (range == null)
/*  340 */       return;  if (this.ranges == null)
/*      */       return; 
/*  342 */     this.ranges.remove(range);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replacedText(NodeImpl node) {
/*  351 */     if (this.ranges != null) {
/*  352 */       int size = this.ranges.size();
/*  353 */       for (int i = 0; i != size; i++) {
/*  354 */         ((RangeImpl)this.ranges.get(i)).receiveReplacedText(node);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void deletedText(NodeImpl node, int offset, int count) {
/*  365 */     if (this.ranges != null) {
/*  366 */       int size = this.ranges.size();
/*  367 */       for (int i = 0; i != size; i++) {
/*  368 */         ((RangeImpl)this.ranges.get(i)).receiveDeletedText(node, offset, count);
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
/*      */   void insertedText(NodeImpl node, int offset, int count) {
/*  380 */     if (this.ranges != null) {
/*  381 */       int size = this.ranges.size();
/*  382 */       for (int i = 0; i != size; i++) {
/*  383 */         ((RangeImpl)this.ranges.get(i)).receiveInsertedText(node, offset, count);
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
/*      */   void splitData(Node node, Node newNode, int offset) {
/*  395 */     if (this.ranges != null) {
/*  396 */       int size = this.ranges.size();
/*  397 */       for (int i = 0; i != size; i++) {
/*  398 */         ((RangeImpl)this.ranges.get(i)).receiveSplitData(node, newNode, offset);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Event createEvent(String type) throws DOMException {
/*  429 */     if (type.equalsIgnoreCase("Events") || "Event".equals(type))
/*  430 */       return new EventImpl(); 
/*  431 */     if (type.equalsIgnoreCase("MutationEvents") || "MutationEvent"
/*  432 */       .equals(type)) {
/*  433 */       return new MutationEventImpl();
/*      */     }
/*  435 */     String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/*  436 */     throw new DOMException((short)9, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setMutationEvents(boolean set) {
/*  445 */     this.mutationEvents = set;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean getMutationEvents() {
/*  452 */     return this.mutationEvents;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setEventListeners(NodeImpl n, List<LEntry> listeners) {
/*  462 */     if (this.eventListeners == null) {
/*  463 */       this.eventListeners = new HashMap<>();
/*      */     }
/*  465 */     if (listeners == null) {
/*  466 */       this.eventListeners.remove(n);
/*  467 */       if (this.eventListeners.isEmpty())
/*      */       {
/*  469 */         this.mutationEvents = false;
/*      */       }
/*      */     } else {
/*  472 */       this.eventListeners.put(n, listeners);
/*      */       
/*  474 */       this.mutationEvents = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<LEntry> getEventListeners(NodeImpl n) {
/*  482 */     if (this.eventListeners == null) {
/*  483 */       return null;
/*      */     }
/*  485 */     return this.eventListeners.get(n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class LEntry
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -8426757059492421631L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String type;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EventListener listener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean useCapture;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     LEntry(String type, EventListener listener, boolean useCapture) {
/*  521 */       this.type = type;
/*  522 */       this.listener = listener;
/*  523 */       this.useCapture = useCapture;
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
/*      */   protected void addEventListener(NodeImpl node, String type, EventListener listener, boolean useCapture) {
/*  545 */     if (type == null || type.equals("") || listener == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  550 */     removeEventListener(node, type, listener, useCapture);
/*      */     
/*  552 */     List<LEntry> nodeListeners = getEventListeners(node);
/*  553 */     if (nodeListeners == null) {
/*  554 */       nodeListeners = new ArrayList<>();
/*  555 */       setEventListeners(node, nodeListeners);
/*      */     } 
/*  557 */     nodeListeners.add(new LEntry(type, listener, useCapture));
/*      */ 
/*      */     
/*  560 */     LCount lc = LCount.lookup(type);
/*  561 */     if (useCapture) {
/*  562 */       lc.captures++;
/*  563 */       lc.total++;
/*      */     } else {
/*      */       
/*  566 */       lc.bubbles++;
/*  567 */       lc.total++;
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
/*      */   protected void removeEventListener(NodeImpl node, String type, EventListener listener, boolean useCapture) {
/*  589 */     if (type == null || type.equals("") || listener == null)
/*      */       return; 
/*  591 */     List<LEntry> nodeListeners = getEventListeners(node);
/*  592 */     if (nodeListeners == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  598 */     for (int i = nodeListeners.size() - 1; i >= 0; i--) {
/*  599 */       LEntry le = nodeListeners.get(i);
/*  600 */       if (le.useCapture == useCapture && le.listener == listener && le.type
/*  601 */         .equals(type)) {
/*  602 */         nodeListeners.remove(i);
/*      */         
/*  604 */         if (nodeListeners.isEmpty()) {
/*  605 */           setEventListeners(node, (List<LEntry>)null);
/*      */         }
/*      */         
/*  608 */         LCount lc = LCount.lookup(type);
/*  609 */         if (useCapture) {
/*  610 */           lc.captures--;
/*  611 */           lc.total--;
/*      */           break;
/*      */         } 
/*  614 */         lc.bubbles--;
/*  615 */         lc.total--;
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void copyEventListeners(NodeImpl src, NodeImpl tgt) {
/*  625 */     List<LEntry> nodeListeners = getEventListeners(src);
/*  626 */     if (nodeListeners == null) {
/*      */       return;
/*      */     }
/*  629 */     setEventListeners(tgt, new ArrayList<>(nodeListeners));
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
/*      */   protected boolean dispatchEvent(NodeImpl node, Event event) {
/*  684 */     if (event == null) return false;
/*      */ 
/*      */ 
/*      */     
/*  688 */     EventImpl evt = (EventImpl)event;
/*      */ 
/*      */ 
/*      */     
/*  692 */     if (!evt.initialized || evt.type == null || evt.type.equals("")) {
/*  693 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "UNSPECIFIED_EVENT_TYPE_ERR", null);
/*  694 */       throw new EventException((short)0, msg);
/*      */     } 
/*      */ 
/*      */     
/*  698 */     LCount lc = LCount.lookup(evt.getType());
/*  699 */     if (lc.total == 0) {
/*  700 */       return evt.preventDefault;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  706 */     evt.target = node;
/*  707 */     evt.stopPropagation = false;
/*  708 */     evt.preventDefault = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  719 */     List<Node> pv = new ArrayList<>(10);
/*  720 */     Node p = node;
/*  721 */     Node n = p.getParentNode();
/*  722 */     while (n != null) {
/*  723 */       pv.add(n);
/*  724 */       p = n;
/*  725 */       n = n.getParentNode();
/*      */     } 
/*      */ 
/*      */     
/*  729 */     if (lc.captures > 0) {
/*  730 */       evt.eventPhase = 1;
/*      */ 
/*      */       
/*  733 */       for (int j = pv.size() - 1; j >= 0 && 
/*  734 */         !evt.stopPropagation; j--) {
/*      */ 
/*      */ 
/*      */         
/*  738 */         NodeImpl nn = (NodeImpl)pv.get(j);
/*  739 */         evt.currentTarget = nn;
/*  740 */         List<LEntry> nodeListeners = getEventListeners(nn);
/*  741 */         if (nodeListeners != null) {
/*  742 */           List<LEntry> nl = (List<LEntry>)((ArrayList)nodeListeners).clone();
/*      */           
/*  744 */           int nlsize = nl.size();
/*  745 */           for (int i = 0; i < nlsize; i++) {
/*  746 */             LEntry le = nl.get(i);
/*  747 */             if (le.useCapture && le.type.equals(evt.type) && nodeListeners
/*  748 */               .contains(le)) {
/*      */               try {
/*  750 */                 le.listener.handleEvent(evt);
/*      */               }
/*  752 */               catch (Exception exception) {}
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  763 */     if (lc.bubbles > 0) {
/*      */ 
/*      */ 
/*      */       
/*  767 */       evt.eventPhase = 2;
/*  768 */       evt.currentTarget = node;
/*  769 */       List<LEntry> nodeListeners = getEventListeners(node);
/*  770 */       if (!evt.stopPropagation && nodeListeners != null) {
/*  771 */         List<LEntry> nl = (List<LEntry>)((ArrayList)nodeListeners).clone();
/*      */         
/*  773 */         int nlsize = nl.size();
/*  774 */         for (int i = 0; i < nlsize; i++) {
/*  775 */           LEntry le = nl.get(i);
/*  776 */           if (!le.useCapture && le.type.equals(evt.type) && nodeListeners
/*  777 */             .contains(le)) {
/*      */             try {
/*  779 */               le.listener.handleEvent(evt);
/*      */             }
/*  781 */             catch (Exception exception) {}
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  792 */       if (evt.bubbles) {
/*  793 */         evt.eventPhase = 3;
/*  794 */         int pvsize = pv.size();
/*  795 */         for (int j = 0; j < pvsize && 
/*  796 */           !evt.stopPropagation; j++) {
/*      */ 
/*      */ 
/*      */           
/*  800 */           NodeImpl nn = (NodeImpl)pv.get(j);
/*  801 */           evt.currentTarget = nn;
/*  802 */           nodeListeners = getEventListeners(nn);
/*  803 */           if (nodeListeners != null) {
/*  804 */             List<LEntry> nl = (List<LEntry>)((ArrayList)nodeListeners).clone();
/*      */ 
/*      */             
/*  807 */             int nlsize = nl.size();
/*  808 */             for (int i = 0; i < nlsize; i++) {
/*  809 */               LEntry le = nl.get(i);
/*  810 */               if (!le.useCapture && le.type.equals(evt.type) && nodeListeners
/*  811 */                 .contains(le)) {
/*      */                 try {
/*  813 */                   le.listener.handleEvent(evt);
/*      */                 }
/*  815 */                 catch (Exception exception) {}
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  830 */     if (lc.defaults <= 0 || !evt.cancelable || !evt.preventDefault);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  836 */     return evt.preventDefault;
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
/*      */   protected void dispatchEventToSubtree(Node n, Event e) {
/*  856 */     ((NodeImpl)n).dispatchEvent(e);
/*  857 */     if (n.getNodeType() == 1) {
/*  858 */       NamedNodeMap a = n.getAttributes();
/*  859 */       for (int i = a.getLength() - 1; i >= 0; i--)
/*  860 */         dispatchingEventToSubtree(a.item(i), e); 
/*      */     } 
/*  862 */     dispatchingEventToSubtree(n.getFirstChild(), e);
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
/*      */   protected void dispatchingEventToSubtree(Node n, Event e) {
/*  874 */     if (n == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  880 */     ((NodeImpl)n).dispatchEvent(e);
/*  881 */     if (n.getNodeType() == 1) {
/*  882 */       NamedNodeMap a = n.getAttributes();
/*  883 */       for (int i = a.getLength() - 1; i >= 0; i--)
/*  884 */         dispatchingEventToSubtree(a.item(i), e); 
/*      */     } 
/*  886 */     dispatchingEventToSubtree(n.getFirstChild(), e);
/*  887 */     dispatchingEventToSubtree(n.getNextSibling(), e);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class EnclosingAttr
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 5208387723391647216L;
/*      */ 
/*      */ 
/*      */     
/*      */     AttrImpl node;
/*      */ 
/*      */ 
/*      */     
/*      */     String oldvalue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dispatchAggregateEvents(NodeImpl node, EnclosingAttr ea) {
/*  911 */     if (ea != null) {
/*  912 */       dispatchAggregateEvents(node, ea.node, ea.oldvalue, (short)1);
/*      */     } else {
/*      */       
/*  915 */       dispatchAggregateEvents(node, (AttrImpl)null, (String)null, (short)0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dispatchAggregateEvents(NodeImpl node, AttrImpl enclosingAttr, String oldvalue, short change) {
/*  947 */     NodeImpl owner = null;
/*  948 */     if (enclosingAttr != null) {
/*  949 */       LCount lCount = LCount.lookup("DOMAttrModified");
/*  950 */       owner = (NodeImpl)enclosingAttr.getOwnerElement();
/*  951 */       if (lCount.total > 0 && 
/*  952 */         owner != null) {
/*  953 */         MutationEventImpl me = new MutationEventImpl();
/*  954 */         me.initMutationEvent("DOMAttrModified", true, false, enclosingAttr, oldvalue, enclosingAttr
/*      */ 
/*      */             
/*  957 */             .getNodeValue(), enclosingAttr
/*  958 */             .getNodeName(), change);
/*      */         
/*  960 */         owner.dispatchEvent(me);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  968 */     LCount lc = LCount.lookup("DOMSubtreeModified");
/*  969 */     if (lc.total > 0) {
/*  970 */       MutationEvent me = new MutationEventImpl();
/*  971 */       me.initMutationEvent("DOMSubtreeModified", true, false, null, null, null, null, (short)0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  978 */       if (enclosingAttr != null) {
/*  979 */         dispatchEvent(enclosingAttr, me);
/*  980 */         if (owner != null) {
/*  981 */           dispatchEvent(owner, me);
/*      */         }
/*      */       } else {
/*  984 */         dispatchEvent(node, me);
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
/*      */   protected void saveEnclosingAttr(NodeImpl node) {
/*  996 */     this.savedEnclosingAttr = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1001 */     LCount lc = LCount.lookup("DOMAttrModified");
/* 1002 */     if (lc.total > 0) {
/* 1003 */       NodeImpl eventAncestor = node;
/*      */       while (true) {
/* 1005 */         if (eventAncestor == null)
/*      */           return; 
/* 1007 */         int type = eventAncestor.getNodeType();
/* 1008 */         if (type == 2) {
/* 1009 */           EnclosingAttr retval = new EnclosingAttr();
/* 1010 */           retval.node = (AttrImpl)eventAncestor;
/* 1011 */           retval.oldvalue = retval.node.getNodeValue();
/* 1012 */           this.savedEnclosingAttr = retval;
/*      */           return;
/*      */         } 
/* 1015 */         if (type == 5) {
/* 1016 */           eventAncestor = eventAncestor.parentNode(); continue;
/* 1017 */         }  if (type == 3) {
/* 1018 */           eventAncestor = eventAncestor.parentNode();
/*      */           continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void modifyingCharacterData(NodeImpl node, boolean replace) {
/* 1030 */     if (this.mutationEvents && 
/* 1031 */       !replace) {
/* 1032 */       saveEnclosingAttr(node);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void modifiedCharacterData(NodeImpl node, String oldvalue, String value, boolean replace) {
/* 1041 */     if (this.mutationEvents && 
/* 1042 */       !replace) {
/*      */ 
/*      */       
/* 1045 */       LCount lc = LCount.lookup("DOMCharacterDataModified");
/* 1046 */       if (lc.total > 0) {
/* 1047 */         MutationEvent me = new MutationEventImpl();
/* 1048 */         me.initMutationEvent("DOMCharacterDataModified", true, false, null, oldvalue, value, null, (short)0);
/*      */ 
/*      */ 
/*      */         
/* 1052 */         dispatchEvent(node, me);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1057 */       dispatchAggregateEvents(node, this.savedEnclosingAttr);
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
/*      */   void replacedCharacterData(NodeImpl node, String oldvalue, String value) {
/* 1070 */     modifiedCharacterData(node, oldvalue, value, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void insertingNode(NodeImpl node, boolean replace) {
/* 1079 */     if (this.mutationEvents && 
/* 1080 */       !replace) {
/* 1081 */       saveEnclosingAttr(node);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void insertedNode(NodeImpl node, NodeImpl newInternal, boolean replace) {
/* 1090 */     if (this.mutationEvents) {
/*      */ 
/*      */ 
/*      */       
/* 1094 */       LCount lc = LCount.lookup("DOMNodeInserted");
/* 1095 */       if (lc.total > 0) {
/* 1096 */         MutationEventImpl me = new MutationEventImpl();
/* 1097 */         me.initMutationEvent("DOMNodeInserted", true, false, node, null, null, null, (short)0);
/*      */ 
/*      */         
/* 1100 */         dispatchEvent(newInternal, me);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1105 */       lc = LCount.lookup("DOMNodeInsertedIntoDocument");
/*      */       
/* 1107 */       if (lc.total > 0) {
/* 1108 */         NodeImpl eventAncestor = node;
/* 1109 */         if (this.savedEnclosingAttr != null)
/*      */         {
/* 1111 */           eventAncestor = (NodeImpl)this.savedEnclosingAttr.node.getOwnerElement(); } 
/* 1112 */         if (eventAncestor != null) {
/* 1113 */           NodeImpl p = eventAncestor;
/* 1114 */           while (p != null) {
/* 1115 */             eventAncestor = p;
/*      */ 
/*      */             
/* 1118 */             if (p.getNodeType() == 2) {
/* 1119 */               p = (NodeImpl)((AttrImpl)p).getOwnerElement();
/*      */               continue;
/*      */             } 
/* 1122 */             p = p.parentNode();
/*      */           } 
/*      */           
/* 1125 */           if (eventAncestor.getNodeType() == 9) {
/* 1126 */             MutationEventImpl me = new MutationEventImpl();
/* 1127 */             me.initMutationEvent("DOMNodeInsertedIntoDocument", false, false, null, null, null, null, (short)0);
/*      */ 
/*      */ 
/*      */             
/* 1131 */             dispatchEventToSubtree(newInternal, me);
/*      */           } 
/*      */         } 
/*      */       } 
/* 1135 */       if (!replace)
/*      */       {
/*      */         
/* 1138 */         dispatchAggregateEvents(node, this.savedEnclosingAttr);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1143 */     if (this.ranges != null) {
/* 1144 */       int size = this.ranges.size();
/* 1145 */       for (int i = 0; i != size; i++) {
/* 1146 */         ((RangeImpl)this.ranges.get(i)).insertedNodeFromDOM(newInternal);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removingNode(NodeImpl node, NodeImpl oldChild, boolean replace) {
/* 1157 */     if (this.iterators != null) {
/* 1158 */       int size = this.iterators.size();
/* 1159 */       for (int i = 0; i != size; i++) {
/* 1160 */         ((NodeIteratorImpl)this.iterators.get(i)).removeNode(oldChild);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1165 */     if (this.ranges != null) {
/* 1166 */       int size = this.ranges.size();
/* 1167 */       for (int i = 0; i != size; i++) {
/* 1168 */         ((RangeImpl)this.ranges.get(i)).removeNode(oldChild);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1173 */     if (this.mutationEvents) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1178 */       if (!replace) {
/* 1179 */         saveEnclosingAttr(node);
/*      */       }
/*      */       
/* 1182 */       LCount lc = LCount.lookup("DOMNodeRemoved");
/* 1183 */       if (lc.total > 0) {
/* 1184 */         MutationEventImpl me = new MutationEventImpl();
/* 1185 */         me.initMutationEvent("DOMNodeRemoved", true, false, node, null, null, null, (short)0);
/*      */ 
/*      */         
/* 1188 */         dispatchEvent(oldChild, me);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1193 */       lc = LCount.lookup("DOMNodeRemovedFromDocument");
/*      */       
/* 1195 */       if (lc.total > 0) {
/* 1196 */         NodeImpl eventAncestor = this;
/* 1197 */         if (this.savedEnclosingAttr != null)
/*      */         {
/* 1199 */           eventAncestor = (NodeImpl)this.savedEnclosingAttr.node.getOwnerElement(); } 
/* 1200 */         if (eventAncestor != null) {
/* 1201 */           NodeImpl p = eventAncestor.parentNode();
/* 1202 */           for (; p != null; p = p.parentNode()) {
/* 1203 */             eventAncestor = p;
/*      */           }
/* 1205 */           if (eventAncestor.getNodeType() == 9) {
/* 1206 */             MutationEventImpl me = new MutationEventImpl();
/* 1207 */             me.initMutationEvent("DOMNodeRemovedFromDocument", false, false, null, null, null, null, (short)0);
/*      */ 
/*      */ 
/*      */             
/* 1211 */             dispatchEventToSubtree(oldChild, me);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removedNode(NodeImpl node, boolean replace) {
/* 1222 */     if (this.mutationEvents)
/*      */     {
/*      */ 
/*      */       
/* 1226 */       if (!replace) {
/* 1227 */         dispatchAggregateEvents(node, this.savedEnclosingAttr);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replacingNode(NodeImpl node) {
/* 1236 */     if (this.mutationEvents) {
/* 1237 */       saveEnclosingAttr(node);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replacingData(NodeImpl node) {
/* 1245 */     if (this.mutationEvents) {
/* 1246 */       saveEnclosingAttr(node);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replacedNode(NodeImpl node) {
/* 1254 */     if (this.mutationEvents) {
/* 1255 */       dispatchAggregateEvents(node, this.savedEnclosingAttr);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void modifiedAttrValue(AttrImpl attr, String oldvalue) {
/* 1263 */     if (this.mutationEvents)
/*      */     {
/* 1265 */       dispatchAggregateEvents(attr, attr, oldvalue, (short)1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setAttrNode(AttrImpl attr, AttrImpl previous) {
/* 1274 */     if (this.mutationEvents)
/*      */     {
/* 1276 */       if (previous == null) {
/* 1277 */         dispatchAggregateEvents(attr.ownerNode, attr, (String)null, (short)2);
/*      */       }
/*      */       else {
/*      */         
/* 1281 */         dispatchAggregateEvents(attr.ownerNode, attr, previous
/* 1282 */             .getNodeValue(), (short)1);
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
/*      */   void removedAttrNode(AttrImpl attr, NodeImpl oldOwner, String name) {
/* 1295 */     if (this.mutationEvents) {
/*      */ 
/*      */       
/* 1298 */       LCount lc = LCount.lookup("DOMAttrModified");
/* 1299 */       if (lc.total > 0) {
/* 1300 */         MutationEventImpl me = new MutationEventImpl();
/* 1301 */         me.initMutationEvent("DOMAttrModified", true, false, attr, attr
/*      */             
/* 1303 */             .getNodeValue(), null, name, (short)3);
/*      */         
/* 1305 */         dispatchEvent(oldOwner, me);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1311 */       dispatchAggregateEvents(oldOwner, (AttrImpl)null, (String)null, (short)0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void renamedAttrNode(Attr oldAt, Attr newAt) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void renamedElement(Element oldEl, Element newEl) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 1337 */     Vector<NodeIterator> it = (this.iterators == null) ? null : new Vector<>(this.iterators);
/* 1338 */     Vector<Range> r = (this.ranges == null) ? null : new Vector<>(this.ranges);
/*      */     
/* 1340 */     Hashtable<NodeImpl, Vector<LEntry>> el = null;
/* 1341 */     if (this.eventListeners != null) {
/* 1342 */       el = new Hashtable<>();
/* 1343 */       for (Map.Entry<NodeImpl, List<LEntry>> e : this.eventListeners.entrySet()) {
/* 1344 */         el.put(e.getKey(), new Vector<>(e.getValue()));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1349 */     ObjectOutputStream.PutField pf = out.putFields();
/* 1350 */     pf.put("iterators", it);
/* 1351 */     pf.put("ranges", r);
/* 1352 */     pf.put("eventListeners", el);
/* 1353 */     pf.put("mutationEvents", this.mutationEvents);
/* 1354 */     out.writeFields();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 1361 */     ObjectInputStream.GetField gf = in.readFields();
/* 1362 */     Vector<NodeIterator> it = (Vector<NodeIterator>)gf.get("iterators", (Object)null);
/* 1363 */     Vector<Range> r = (Vector<Range>)gf.get("ranges", (Object)null);
/*      */     
/* 1365 */     Hashtable<NodeImpl, Vector<LEntry>> el = (Hashtable<NodeImpl, Vector<LEntry>>)gf.get("eventListeners", (Object)null);
/*      */     
/* 1367 */     this.mutationEvents = gf.get("mutationEvents", false);
/*      */ 
/*      */     
/* 1370 */     if (it != null) this.iterators = new ArrayList<>(it); 
/* 1371 */     if (r != null) this.ranges = new ArrayList<>(r); 
/* 1372 */     if (el != null) {
/* 1373 */       this.eventListeners = new HashMap<>();
/* 1374 */       for (Map.Entry<NodeImpl, Vector<LEntry>> e : el.entrySet())
/* 1375 */         this.eventListeners.put(e.getKey(), new ArrayList<>(e.getValue())); 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DocumentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */