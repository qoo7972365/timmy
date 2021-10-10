/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class NamedNodeMapImpl
/*     */   implements NamedNodeMap, Serializable
/*     */ {
/*     */   static final long serialVersionUID = -7039242451046758020L;
/*     */   protected short flags;
/*     */   protected static final short READONLY = 1;
/*     */   protected static final short CHANGED = 2;
/*     */   protected static final short HASDEFAULTS = 4;
/*     */   protected List nodes;
/*     */   protected NodeImpl ownerNode;
/*     */   
/*     */   protected NamedNodeMapImpl(NodeImpl ownerNode) {
/*  92 */     this.ownerNode = ownerNode;
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
/*     */   public int getLength() {
/* 106 */     return (this.nodes != null) ? this.nodes.size() : 0;
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
/*     */   public Node item(int index) {
/* 124 */     return (this.nodes != null && index < this.nodes.size()) ? this.nodes
/* 125 */       .get(index) : null;
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
/*     */   public Node getNamedItem(String name) {
/* 137 */     int i = findNamePoint(name, 0);
/* 138 */     return (i < 0) ? null : this.nodes.get(i);
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
/*     */   public Node getNamedItemNS(String namespaceURI, String localName) {
/* 155 */     int i = findNamePoint(namespaceURI, localName);
/* 156 */     return (i < 0) ? null : this.nodes.get(i);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node setNamedItem(Node arg) throws DOMException {
/* 180 */     CoreDocumentImpl ownerDocument = this.ownerNode.ownerDocument();
/* 181 */     if (ownerDocument.errorChecking) {
/* 182 */       if (isReadOnly()) {
/* 183 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
/* 184 */         throw new DOMException((short)7, msg);
/*     */       } 
/* 186 */       if (arg.getOwnerDocument() != ownerDocument) {
/* 187 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
/* 188 */         throw new DOMException((short)4, msg);
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     int i = findNamePoint(arg.getNodeName(), 0);
/* 193 */     NodeImpl previous = null;
/* 194 */     if (i >= 0) {
/* 195 */       previous = this.nodes.get(i);
/* 196 */       this.nodes.set(i, arg);
/*     */     } else {
/* 198 */       i = -1 - i;
/* 199 */       if (null == this.nodes) {
/* 200 */         this.nodes = new ArrayList(5);
/*     */       }
/* 202 */       this.nodes.add(i, arg);
/*     */     } 
/* 204 */     return previous;
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
/*     */   public Node setNamedItemNS(Node arg) throws DOMException {
/* 222 */     CoreDocumentImpl ownerDocument = this.ownerNode.ownerDocument();
/* 223 */     if (ownerDocument.errorChecking) {
/* 224 */       if (isReadOnly()) {
/* 225 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
/* 226 */         throw new DOMException((short)7, msg);
/*     */       } 
/*     */       
/* 229 */       if (arg.getOwnerDocument() != ownerDocument) {
/* 230 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
/* 231 */         throw new DOMException((short)4, msg);
/*     */       } 
/*     */     } 
/*     */     
/* 235 */     int i = findNamePoint(arg.getNamespaceURI(), arg.getLocalName());
/* 236 */     NodeImpl previous = null;
/* 237 */     if (i >= 0) {
/* 238 */       previous = this.nodes.get(i);
/* 239 */       this.nodes.set(i, arg);
/*     */     }
/*     */     else {
/*     */       
/* 243 */       i = findNamePoint(arg.getNodeName(), 0);
/* 244 */       if (i >= 0) {
/* 245 */         previous = this.nodes.get(i);
/* 246 */         this.nodes.add(i, arg);
/*     */       } else {
/* 248 */         i = -1 - i;
/* 249 */         if (null == this.nodes) {
/* 250 */           this.nodes = new ArrayList(5);
/*     */         }
/* 252 */         this.nodes.add(i, arg);
/*     */       } 
/*     */     } 
/* 255 */     return previous;
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
/*     */   public Node removeNamedItem(String name) throws DOMException {
/* 268 */     if (isReadOnly()) {
/* 269 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
/* 270 */       throw new DOMException((short)7, msg);
/*     */     } 
/*     */ 
/*     */     
/* 274 */     int i = findNamePoint(name, 0);
/* 275 */     if (i < 0) {
/* 276 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_FOUND_ERR", null);
/* 277 */       throw new DOMException((short)8, msg);
/*     */     } 
/*     */     
/* 280 */     NodeImpl n = this.nodes.get(i);
/* 281 */     this.nodes.remove(i);
/*     */     
/* 283 */     return n;
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
/*     */   
/*     */   public Node removeNamedItemNS(String namespaceURI, String name) throws DOMException {
/* 304 */     if (isReadOnly()) {
/* 305 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
/* 306 */       throw new DOMException((short)7, msg);
/*     */     } 
/*     */ 
/*     */     
/* 310 */     int i = findNamePoint(namespaceURI, name);
/* 311 */     if (i < 0) {
/* 312 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_FOUND_ERR", null);
/* 313 */       throw new DOMException((short)8, msg);
/*     */     } 
/*     */     
/* 316 */     NodeImpl n = this.nodes.get(i);
/* 317 */     this.nodes.remove(i);
/*     */     
/* 319 */     return n;
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
/*     */   public NamedNodeMapImpl cloneMap(NodeImpl ownerNode) {
/* 333 */     NamedNodeMapImpl newmap = new NamedNodeMapImpl(ownerNode);
/* 334 */     newmap.cloneContent(this);
/* 335 */     return newmap;
/*     */   }
/*     */   
/*     */   protected void cloneContent(NamedNodeMapImpl srcmap) {
/* 339 */     List srcnodes = srcmap.nodes;
/* 340 */     if (srcnodes != null) {
/* 341 */       int size = srcnodes.size();
/* 342 */       if (size != 0) {
/* 343 */         if (this.nodes == null) {
/* 344 */           this.nodes = new ArrayList(size);
/*     */         } else {
/*     */           
/* 347 */           this.nodes.clear();
/*     */         } 
/* 349 */         for (int i = 0; i < size; i++) {
/* 350 */           NodeImpl n = srcmap.nodes.get(i);
/* 351 */           NodeImpl clone = (NodeImpl)n.cloneNode(true);
/* 352 */           clone.isSpecified(n.isSpecified());
/* 353 */           this.nodes.add(clone);
/*     */         } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   void setReadOnly(boolean readOnly, boolean deep) {
/* 375 */     isReadOnly(readOnly);
/* 376 */     if (deep && this.nodes != null) {
/* 377 */       for (int i = this.nodes.size() - 1; i >= 0; i--) {
/* 378 */         ((NodeImpl)this.nodes.get(i)).setReadOnly(readOnly, deep);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean getReadOnly() {
/* 388 */     return isReadOnly();
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
/*     */   protected void setOwnerDocument(CoreDocumentImpl doc) {
/* 401 */     if (this.nodes != null) {
/* 402 */       int size = this.nodes.size();
/* 403 */       for (int i = 0; i < size; i++) {
/* 404 */         ((NodeImpl)item(i)).setOwnerDocument(doc);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   final boolean isReadOnly() {
/* 410 */     return ((this.flags & 0x1) != 0);
/*     */   }
/*     */   
/*     */   final void isReadOnly(boolean value) {
/* 414 */     this.flags = (short)(value ? (this.flags | 0x1) : (this.flags & 0xFFFFFFFE));
/*     */   }
/*     */   
/*     */   final boolean changed() {
/* 418 */     return ((this.flags & 0x2) != 0);
/*     */   }
/*     */   
/*     */   final void changed(boolean value) {
/* 422 */     this.flags = (short)(value ? (this.flags | 0x2) : (this.flags & 0xFFFFFFFD));
/*     */   }
/*     */   
/*     */   final boolean hasDefaults() {
/* 426 */     return ((this.flags & 0x4) != 0);
/*     */   }
/*     */   
/*     */   final void hasDefaults(boolean value) {
/* 430 */     this.flags = (short)(value ? (this.flags | 0x4) : (this.flags & 0xFFFFFFFB));
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
/*     */ 
/*     */   
/*     */   protected int findNamePoint(String name, int start) {
/* 452 */     int i = 0;
/* 453 */     if (this.nodes != null) {
/* 454 */       int first = start;
/* 455 */       int last = this.nodes.size() - 1;
/*     */       
/* 457 */       while (first <= last) {
/* 458 */         i = (first + last) / 2;
/* 459 */         int test = name.compareTo(((Node)this.nodes.get(i)).getNodeName());
/* 460 */         if (test == 0) {
/* 461 */           return i;
/*     */         }
/* 463 */         if (test < 0) {
/* 464 */           last = i - 1;
/*     */           continue;
/*     */         } 
/* 467 */         first = i + 1;
/*     */       } 
/*     */ 
/*     */       
/* 471 */       if (first > i) {
/* 472 */         i = first;
/*     */       }
/*     */     } 
/*     */     
/* 476 */     return -1 - i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int findNamePoint(String namespaceURI, String name) {
/* 485 */     if (this.nodes == null) return -1; 
/* 486 */     if (name == null) return -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 495 */     int size = this.nodes.size();
/* 496 */     for (int i = 0; i < size; i++) {
/* 497 */       NodeImpl a = this.nodes.get(i);
/* 498 */       String aNamespaceURI = a.getNamespaceURI();
/* 499 */       String aLocalName = a.getLocalName();
/* 500 */       if (namespaceURI == null) {
/* 501 */         if (aNamespaceURI == null && (name
/*     */           
/* 503 */           .equals(aLocalName) || (aLocalName == null && name
/*     */           
/* 505 */           .equals(a.getNodeName())))) {
/* 506 */           return i;
/*     */         }
/* 508 */       } else if (namespaceURI.equals(aNamespaceURI) && name
/*     */         
/* 510 */         .equals(aLocalName)) {
/* 511 */         return i;
/*     */       } 
/*     */     } 
/* 514 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean precedes(Node a, Node b) {
/* 521 */     if (this.nodes != null) {
/* 522 */       int size = this.nodes.size();
/* 523 */       for (int i = 0; i < size; i++) {
/* 524 */         Node n = this.nodes.get(i);
/* 525 */         if (n == a) return true; 
/* 526 */         if (n == b) return false; 
/*     */       } 
/*     */     } 
/* 529 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeItem(int index) {
/* 537 */     if (this.nodes != null && index < this.nodes.size()) {
/* 538 */       this.nodes.remove(index);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getItem(int index) {
/* 544 */     if (this.nodes != null) {
/* 545 */       return this.nodes.get(index);
/*     */     }
/* 547 */     return null;
/*     */   }
/*     */   
/*     */   protected int addItem(Node arg) {
/* 551 */     int i = findNamePoint(arg.getNamespaceURI(), arg.getLocalName());
/* 552 */     if (i >= 0) {
/* 553 */       this.nodes.set(i, arg);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 558 */       i = findNamePoint(arg.getNodeName(), 0);
/* 559 */       if (i >= 0) {
/* 560 */         this.nodes.add(i, arg);
/*     */       } else {
/*     */         
/* 563 */         i = -1 - i;
/* 564 */         if (null == this.nodes) {
/* 565 */           this.nodes = new ArrayList(5);
/*     */         }
/* 567 */         this.nodes.add(i, arg);
/*     */       } 
/*     */     } 
/* 570 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ArrayList cloneMap(ArrayList list) {
/* 580 */     if (list == null) {
/* 581 */       list = new ArrayList(5);
/*     */     }
/* 583 */     list.clear();
/* 584 */     if (this.nodes != null) {
/* 585 */       int size = this.nodes.size();
/* 586 */       for (int i = 0; i < size; i++) {
/* 587 */         list.add(this.nodes.get(i));
/*     */       }
/*     */     } 
/* 590 */     return list;
/*     */   }
/*     */   
/*     */   protected int getNamedItemIndex(String namespaceURI, String localName) {
/* 594 */     return findNamePoint(namespaceURI, localName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 601 */     if (this.nodes != null) {
/* 602 */       this.nodes.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 608 */     in.defaultReadObject();
/* 609 */     if (this.nodes != null)
/*     */     {
/* 611 */       this.nodes = new ArrayList(this.nodes);
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 616 */     List<?> oldNodes = this.nodes;
/*     */     try {
/* 618 */       if (oldNodes != null) {
/* 619 */         this.nodes = new Vector(oldNodes);
/*     */       }
/* 621 */       out.defaultWriteObject();
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 626 */       this.nodes = oldNodes;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/NamedNodeMapImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */