/*      */ package com.sun.org.apache.xerces.internal.dom;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Map;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.DocumentType;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.w3c.dom.UserDataHandler;
/*      */ import org.w3c.dom.events.Event;
/*      */ import org.w3c.dom.events.EventListener;
/*      */ import org.w3c.dom.events.EventTarget;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class NodeImpl
/*      */   implements Node, NodeList, EventTarget, Cloneable, Serializable
/*      */ {
/*      */   public static final short TREE_POSITION_PRECEDING = 1;
/*      */   public static final short TREE_POSITION_FOLLOWING = 2;
/*      */   public static final short TREE_POSITION_ANCESTOR = 4;
/*      */   public static final short TREE_POSITION_DESCENDANT = 8;
/*      */   public static final short TREE_POSITION_EQUIVALENT = 16;
/*      */   public static final short TREE_POSITION_SAME_NODE = 32;
/*      */   public static final short TREE_POSITION_DISCONNECTED = 0;
/*      */   public static final short DOCUMENT_POSITION_DISCONNECTED = 1;
/*      */   public static final short DOCUMENT_POSITION_PRECEDING = 2;
/*      */   public static final short DOCUMENT_POSITION_FOLLOWING = 4;
/*      */   public static final short DOCUMENT_POSITION_CONTAINS = 8;
/*      */   public static final short DOCUMENT_POSITION_IS_CONTAINED = 16;
/*      */   public static final short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;
/*      */   static final long serialVersionUID = -6316591992167219696L;
/*      */   public static final short ELEMENT_DEFINITION_NODE = 21;
/*      */   protected NodeImpl ownerNode;
/*      */   protected short flags;
/*      */   protected static final short READONLY = 1;
/*      */   protected static final short SYNCDATA = 2;
/*      */   protected static final short SYNCCHILDREN = 4;
/*      */   protected static final short OWNED = 8;
/*      */   protected static final short FIRSTCHILD = 16;
/*      */   protected static final short SPECIFIED = 32;
/*      */   protected static final short IGNORABLEWS = 64;
/*      */   protected static final short HASSTRING = 128;
/*      */   protected static final short NORMALIZED = 256;
/*      */   protected static final short ID = 512;
/*      */   
/*      */   protected NodeImpl(CoreDocumentImpl ownerDocument) {
/*  177 */     this.ownerNode = ownerDocument;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NodeImpl() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract short getNodeType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getNodeName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNodeValue() throws DOMException {
/*  204 */     return null;
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
/*      */   public void setNodeValue(String x) throws DOMException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node appendChild(Node newChild) throws DOMException {
/*  237 */     return insertBefore(newChild, null);
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
/*      */   public Node cloneNode(boolean deep) {
/*      */     NodeImpl newnode;
/*  265 */     if (needsSyncData()) {
/*  266 */       synchronizeData();
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  271 */       newnode = (NodeImpl)clone();
/*      */     }
/*  273 */     catch (CloneNotSupportedException e) {
/*      */ 
/*      */       
/*  276 */       throw new RuntimeException("**Internal Error**" + e);
/*      */     } 
/*      */ 
/*      */     
/*  280 */     newnode.ownerNode = ownerDocument();
/*  281 */     newnode.isOwned(false);
/*      */ 
/*      */ 
/*      */     
/*  285 */     newnode.isReadOnly(false);
/*      */     
/*  287 */     ownerDocument().callUserDataHandlers(this, newnode, (short)1);
/*      */ 
/*      */     
/*  290 */     return newnode;
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
/*      */   public Document getOwnerDocument() {
/*  302 */     if (isOwned()) {
/*  303 */       return this.ownerNode.ownerDocument();
/*      */     }
/*  305 */     return (Document)this.ownerNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CoreDocumentImpl ownerDocument() {
/*  316 */     if (isOwned()) {
/*  317 */       return this.ownerNode.ownerDocument();
/*      */     }
/*  319 */     return (CoreDocumentImpl)this.ownerNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setOwnerDocument(CoreDocumentImpl doc) {
/*  328 */     if (needsSyncData()) {
/*  329 */       synchronizeData();
/*      */     }
/*      */ 
/*      */     
/*  333 */     if (!isOwned()) {
/*  334 */       this.ownerNode = doc;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getNodeNumber() {
/*  343 */     CoreDocumentImpl cd = (CoreDocumentImpl)getOwnerDocument();
/*  344 */     int nodeNumber = cd.getNodeNumber(this);
/*  345 */     return nodeNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getParentNode() {
/*  355 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   NodeImpl parentNode() {
/*  362 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Node getNextSibling() {
/*  367 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Node getPreviousSibling() {
/*  372 */     return null;
/*      */   }
/*      */   
/*      */   ChildNode previousSibling() {
/*  376 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NamedNodeMap getAttributes() {
/*  387 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasAttributes() {
/*  398 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasChildNodes() {
/*  409 */     return false;
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
/*      */   public NodeList getChildNodes() {
/*  426 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getFirstChild() {
/*  435 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getLastChild() {
/*  444 */     return null;
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
/*      */   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
/*  480 */     throw new DOMException((short)3, 
/*  481 */         DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null));
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
/*      */   public Node removeChild(Node oldChild) throws DOMException {
/*  502 */     throw new DOMException((short)8, 
/*  503 */         DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_FOUND_ERR", null));
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
/*      */   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
/*  533 */     throw new DOMException((short)3, 
/*  534 */         DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null));
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
/*      */   public int getLength() {
/*  551 */     return 0;
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
/*      */   public Node item(int index) {
/*  565 */     return null;
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
/*      */   public void normalize() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSupported(String feature, String version) {
/*  612 */     return ownerDocument().getImplementation().hasFeature(feature, version);
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
/*      */   public String getNamespaceURI() {
/*  635 */     return null;
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
/*      */   public String getPrefix() {
/*  654 */     return null;
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
/*      */   public void setPrefix(String prefix) throws DOMException {
/*  681 */     throw new DOMException((short)14, 
/*  682 */         DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null));
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
/*      */   public String getLocalName() {
/*  700 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEventListener(String type, EventListener listener, boolean useCapture) {
/*  710 */     ownerDocument().addEventListener(this, type, listener, useCapture);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEventListener(String type, EventListener listener, boolean useCapture) {
/*  716 */     ownerDocument().removeEventListener(this, type, listener, useCapture);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean dispatchEvent(Event event) {
/*  721 */     return ownerDocument().dispatchEvent(this, event);
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
/*      */   public String getBaseURI() {
/*  753 */     return null;
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
/*      */   public short compareTreePosition(Node other) {
/*  804 */     if (this == other) {
/*  805 */       return 48;
/*      */     }
/*      */     
/*  808 */     short thisType = getNodeType();
/*  809 */     short otherType = other.getNodeType();
/*      */ 
/*      */     
/*  812 */     if (thisType == 6 || thisType == 12 || otherType == 6 || otherType == 12)
/*      */     {
/*      */ 
/*      */       
/*  816 */       return 0;
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
/*  827 */     Node thisAncestor = this;
/*  828 */     Node otherAncestor = other;
/*  829 */     int thisDepth = 0;
/*  830 */     int otherDepth = 0; Node node;
/*  831 */     for (node = this; node != null; node = node.getParentNode()) {
/*  832 */       thisDepth++;
/*  833 */       if (node == other)
/*      */       {
/*  835 */         return 5; } 
/*  836 */       thisAncestor = node;
/*      */     } 
/*      */     
/*  839 */     for (node = other; node != null; node = node.getParentNode()) {
/*  840 */       otherDepth++;
/*  841 */       if (node == this)
/*      */       {
/*  843 */         return 10; } 
/*  844 */       otherAncestor = node;
/*      */     } 
/*      */ 
/*      */     
/*  848 */     Node thisNode = this;
/*  849 */     Node otherNode = other;
/*      */     
/*  851 */     int thisAncestorType = thisAncestor.getNodeType();
/*  852 */     int otherAncestorType = otherAncestor.getNodeType();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  857 */     if (thisAncestorType == 2) {
/*  858 */       thisNode = ((AttrImpl)thisAncestor).getOwnerElement();
/*      */     }
/*  860 */     if (otherAncestorType == 2) {
/*  861 */       otherNode = ((AttrImpl)otherAncestor).getOwnerElement();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  866 */     if (thisAncestorType == 2 && otherAncestorType == 2 && thisNode == otherNode)
/*      */     {
/*      */       
/*  869 */       return 16;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  876 */     if (thisAncestorType == 2) {
/*  877 */       thisDepth = 0;
/*  878 */       for (node = thisNode; node != null; node = node.getParentNode()) {
/*  879 */         thisDepth++;
/*  880 */         if (node == otherNode)
/*      */         {
/*      */           
/*  883 */           return 1;
/*      */         }
/*  885 */         thisAncestor = node;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  891 */     if (otherAncestorType == 2) {
/*  892 */       otherDepth = 0;
/*  893 */       for (node = otherNode; node != null; node = node.getParentNode()) {
/*  894 */         otherDepth++;
/*  895 */         if (node == thisNode)
/*      */         {
/*      */           
/*  898 */           return 2; } 
/*  899 */         otherAncestor = node;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  905 */     if (thisAncestor != otherAncestor) {
/*  906 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  912 */     if (thisDepth > otherDepth) {
/*  913 */       for (int i = 0; i < thisDepth - otherDepth; i++) {
/*  914 */         thisNode = thisNode.getParentNode();
/*      */       }
/*      */ 
/*      */       
/*  918 */       if (thisNode == otherNode) {
/*  919 */         return 1;
/*      */       }
/*      */     } else {
/*      */       
/*  923 */       for (int i = 0; i < otherDepth - thisDepth; i++) {
/*  924 */         otherNode = otherNode.getParentNode();
/*      */       }
/*      */ 
/*      */       
/*  928 */       if (otherNode == thisNode) {
/*  929 */         return 2;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  935 */     Node thisNodeP = thisNode.getParentNode();
/*  936 */     Node otherNodeP = otherNode.getParentNode();
/*  937 */     while (thisNodeP != otherNodeP) {
/*  938 */       thisNode = thisNodeP;
/*  939 */       otherNode = otherNodeP;
/*  940 */       thisNodeP = thisNodeP.getParentNode();
/*  941 */       otherNodeP = otherNodeP.getParentNode();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  948 */     Node current = thisNodeP.getFirstChild();
/*  949 */     for (; current != null; 
/*  950 */       current = current.getNextSibling()) {
/*  951 */       if (current == otherNode) {
/*  952 */         return 1;
/*      */       }
/*  954 */       if (current == thisNode) {
/*  955 */         return 2;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  960 */     return 0;
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
/*      */   public short compareDocumentPosition(Node other) throws DOMException {
/*      */     Document thisOwnerDoc, otherOwnerDoc;
/*      */     DocumentType container;
/*  974 */     if (this == other) {
/*  975 */       return 0;
/*      */     }
/*      */     
/*      */     try {
/*  979 */       NodeImpl nodeImpl = (NodeImpl)other;
/*  980 */     } catch (ClassCastException e) {
/*      */       
/*  982 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/*      */       
/*  984 */       throw new DOMException((short)9, msg);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  989 */     if (getNodeType() == 9) {
/*  990 */       thisOwnerDoc = (Document)this;
/*      */     } else {
/*  992 */       thisOwnerDoc = getOwnerDocument();
/*  993 */     }  if (other.getNodeType() == 9) {
/*  994 */       otherOwnerDoc = (Document)other;
/*      */     } else {
/*  996 */       otherOwnerDoc = other.getOwnerDocument();
/*      */     } 
/*      */ 
/*      */     
/* 1000 */     if (thisOwnerDoc != otherOwnerDoc && thisOwnerDoc != null && otherOwnerDoc != null) {
/*      */ 
/*      */ 
/*      */       
/* 1004 */       int otherDocNum = ((CoreDocumentImpl)otherOwnerDoc).getNodeNumber();
/* 1005 */       int thisDocNum = ((CoreDocumentImpl)thisOwnerDoc).getNodeNumber();
/* 1006 */       if (otherDocNum > thisDocNum) {
/* 1007 */         return 37;
/*      */       }
/*      */ 
/*      */       
/* 1011 */       return 35;
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
/* 1025 */     Node thisAncestor = this;
/* 1026 */     Node otherAncestor = other;
/*      */     
/* 1028 */     int thisDepth = 0;
/* 1029 */     int otherDepth = 0; Node node;
/* 1030 */     for (node = this; node != null; node = node.getParentNode()) {
/* 1031 */       thisDepth++;
/* 1032 */       if (node == other)
/*      */       {
/* 1034 */         return 10;
/*      */       }
/* 1036 */       thisAncestor = node;
/*      */     } 
/*      */     
/* 1039 */     for (node = other; node != null; node = node.getParentNode()) {
/* 1040 */       otherDepth++;
/* 1041 */       if (node == this)
/*      */       {
/* 1043 */         return 20;
/*      */       }
/* 1045 */       otherAncestor = node;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1050 */     int thisAncestorType = thisAncestor.getNodeType();
/* 1051 */     int otherAncestorType = otherAncestor.getNodeType();
/* 1052 */     Node thisNode = this;
/* 1053 */     Node otherNode = other;
/*      */ 
/*      */ 
/*      */     
/* 1057 */     switch (thisAncestorType) {
/*      */       case 6:
/*      */       case 12:
/* 1060 */         container = thisOwnerDoc.getDoctype();
/* 1061 */         if (container == otherAncestor) return 10;
/*      */         
/* 1063 */         switch (otherAncestorType) {
/*      */           case 6:
/*      */           case 12:
/* 1066 */             if (thisAncestorType != otherAncestorType)
/*      */             {
/* 1068 */               return (thisAncestorType > otherAncestorType) ? 2 : 4;
/*      */             }
/*      */ 
/*      */             
/* 1072 */             if (thisAncestorType == 12) {
/*      */               
/* 1074 */               if (((NamedNodeMapImpl)container.getNotations()).precedes(otherAncestor, thisAncestor)) {
/* 1075 */                 return 34;
/*      */               }
/*      */               
/* 1078 */               return 36;
/*      */             } 
/*      */             
/* 1081 */             if (((NamedNodeMapImpl)container.getEntities()).precedes(otherAncestor, thisAncestor)) {
/* 1082 */               return 34;
/*      */             }
/*      */             
/* 1085 */             return 36;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1090 */         thisNode = thisAncestor = thisOwnerDoc;
/*      */         break;
/*      */       
/*      */       case 10:
/* 1094 */         if (otherNode == thisOwnerDoc) {
/* 1095 */           return 10;
/*      */         }
/* 1097 */         if (thisOwnerDoc != null && thisOwnerDoc == otherOwnerDoc) {
/* 1098 */           return 4;
/*      */         }
/*      */         break;
/*      */       case 2:
/* 1102 */         thisNode = ((AttrImpl)thisAncestor).getOwnerElement();
/* 1103 */         if (otherAncestorType == 2) {
/* 1104 */           otherNode = ((AttrImpl)otherAncestor).getOwnerElement();
/* 1105 */           if (otherNode == thisNode) {
/* 1106 */             if (((NamedNodeMapImpl)thisNode.getAttributes()).precedes(other, this)) {
/* 1107 */               return 34;
/*      */             }
/*      */             
/* 1110 */             return 36;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1116 */         thisDepth = 0;
/* 1117 */         for (node = thisNode; node != null; node = node.getParentNode()) {
/* 1118 */           thisDepth++;
/* 1119 */           if (node == otherNode)
/*      */           {
/*      */             
/* 1122 */             return 10;
/*      */           }
/*      */           
/* 1125 */           thisAncestor = node;
/*      */         } 
/*      */         break;
/*      */     } 
/* 1129 */     switch (otherAncestorType) {
/*      */       case 6:
/*      */       case 12:
/* 1132 */         container = thisOwnerDoc.getDoctype();
/* 1133 */         if (container == this) return 20;
/*      */         
/* 1135 */         otherNode = otherAncestor = thisOwnerDoc;
/*      */         break;
/*      */       
/*      */       case 10:
/* 1139 */         if (thisNode == otherOwnerDoc) {
/* 1140 */           return 20;
/*      */         }
/* 1142 */         if (otherOwnerDoc != null && thisOwnerDoc == otherOwnerDoc) {
/* 1143 */           return 2;
/*      */         }
/*      */         break;
/*      */       case 2:
/* 1147 */         otherDepth = 0;
/* 1148 */         otherNode = ((AttrImpl)otherAncestor).getOwnerElement();
/* 1149 */         for (node = otherNode; node != null; node = node.getParentNode()) {
/* 1150 */           otherDepth++;
/* 1151 */           if (node == thisNode)
/*      */           {
/*      */             
/* 1154 */             return 20;
/*      */           }
/* 1156 */           otherAncestor = node;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1164 */     if (thisAncestor != otherAncestor) {
/*      */       
/* 1166 */       int thisAncestorNum = ((NodeImpl)thisAncestor).getNodeNumber();
/* 1167 */       int otherAncestorNum = ((NodeImpl)otherAncestor).getNodeNumber();
/*      */       
/* 1169 */       if (thisAncestorNum > otherAncestorNum) {
/* 1170 */         return 37;
/*      */       }
/*      */ 
/*      */       
/* 1174 */       return 35;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1183 */     if (thisDepth > otherDepth) {
/* 1184 */       for (int i = 0; i < thisDepth - otherDepth; i++) {
/* 1185 */         thisNode = thisNode.getParentNode();
/*      */       }
/*      */ 
/*      */       
/* 1189 */       if (thisNode == otherNode)
/*      */       {
/* 1191 */         return 2;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1196 */       for (int i = 0; i < otherDepth - thisDepth; i++) {
/* 1197 */         otherNode = otherNode.getParentNode();
/*      */       }
/*      */ 
/*      */       
/* 1201 */       if (otherNode == thisNode) {
/* 1202 */         return 4;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1208 */     Node thisNodeP = thisNode.getParentNode();
/* 1209 */     Node otherNodeP = otherNode.getParentNode();
/* 1210 */     while (thisNodeP != otherNodeP) {
/* 1211 */       thisNode = thisNodeP;
/* 1212 */       otherNode = otherNodeP;
/* 1213 */       thisNodeP = thisNodeP.getParentNode();
/* 1214 */       otherNodeP = otherNodeP.getParentNode();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1221 */     Node current = thisNodeP.getFirstChild();
/* 1222 */     for (; current != null; 
/* 1223 */       current = current.getNextSibling()) {
/* 1224 */       if (current == otherNode) {
/* 1225 */         return 2;
/*      */       }
/* 1227 */       if (current == thisNode) {
/* 1228 */         return 4;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1233 */     return 0;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTextContent() throws DOMException {
/* 1301 */     return getNodeValue();
/*      */   }
/*      */ 
/*      */   
/*      */   void getTextContent(StringBuffer buf) throws DOMException {
/* 1306 */     String content = getNodeValue();
/* 1307 */     if (content != null) {
/* 1308 */       buf.append(content);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextContent(String textContent) throws DOMException {
/* 1359 */     setNodeValue(textContent);
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
/*      */   public boolean isSameNode(Node other) {
/* 1378 */     return (this == other);
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
/*      */   public boolean isDefaultNamespace(String namespaceURI) {
/*      */     String namespace, prefix;
/*      */     NodeImpl nodeImpl1;
/* 1395 */     short type = getNodeType();
/* 1396 */     switch (type) {
/*      */       case 1:
/* 1398 */         namespace = getNamespaceURI();
/* 1399 */         prefix = getPrefix();
/*      */ 
/*      */         
/* 1402 */         if (prefix == null || prefix.length() == 0) {
/* 1403 */           if (namespaceURI == null) {
/* 1404 */             return (namespace == namespaceURI);
/*      */           }
/* 1406 */           return namespaceURI.equals(namespace);
/*      */         } 
/* 1408 */         if (hasAttributes()) {
/* 1409 */           ElementImpl elem = (ElementImpl)this;
/* 1410 */           NodeImpl attr = (NodeImpl)elem.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/* 1411 */           if (attr != null) {
/* 1412 */             String value = attr.getNodeValue();
/* 1413 */             if (namespaceURI == null) {
/* 1414 */               return (namespace == value);
/*      */             }
/* 1416 */             return namespaceURI.equals(value);
/*      */           } 
/*      */         } 
/*      */         
/* 1420 */         nodeImpl1 = (NodeImpl)getElementAncestor(this);
/* 1421 */         if (nodeImpl1 != null) {
/* 1422 */           return nodeImpl1.isDefaultNamespace(namespaceURI);
/*      */         }
/* 1424 */         return false;
/*      */       
/*      */       case 9:
/* 1427 */         return ((NodeImpl)((Document)this).getDocumentElement()).isDefaultNamespace(namespaceURI);
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/* 1435 */         return false;
/*      */       case 2:
/* 1437 */         if (this.ownerNode.getNodeType() == 1) {
/* 1438 */           return this.ownerNode.isDefaultNamespace(namespaceURI);
/*      */         }
/*      */         
/* 1441 */         return false;
/*      */     } 
/*      */     
/* 1444 */     NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
/* 1445 */     if (ancestor != null) {
/* 1446 */       return ancestor.isDefaultNamespace(namespaceURI);
/*      */     }
/* 1448 */     return false;
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
/*      */   public String lookupPrefix(String namespaceURI) {
/*      */     String namespace;
/* 1469 */     if (namespaceURI == null) {
/* 1470 */       return null;
/*      */     }
/*      */     
/* 1473 */     short type = getNodeType();
/*      */     
/* 1475 */     switch (type) {
/*      */       
/*      */       case 1:
/* 1478 */         namespace = getNamespaceURI();
/* 1479 */         return lookupNamespacePrefix(namespaceURI, (ElementImpl)this);
/*      */       
/*      */       case 9:
/* 1482 */         return ((NodeImpl)((Document)this).getDocumentElement()).lookupPrefix(namespaceURI);
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/* 1490 */         return null;
/*      */       case 2:
/* 1492 */         if (this.ownerNode.getNodeType() == 1) {
/* 1493 */           return this.ownerNode.lookupPrefix(namespaceURI);
/*      */         }
/*      */         
/* 1496 */         return null;
/*      */     } 
/*      */     
/* 1499 */     NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
/* 1500 */     if (ancestor != null) {
/* 1501 */       return ancestor.lookupPrefix(namespaceURI);
/*      */     }
/* 1503 */     return null;
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
/*      */   public String lookupNamespaceURI(String specifiedPrefix) {
/*      */     String namespace, prefix;
/*      */     NodeImpl nodeImpl1;
/* 1518 */     short type = getNodeType();
/* 1519 */     switch (type) {
/*      */       
/*      */       case 1:
/* 1522 */         namespace = getNamespaceURI();
/* 1523 */         prefix = getPrefix();
/* 1524 */         if (namespace != null) {
/*      */           
/* 1526 */           if (specifiedPrefix == null && prefix == specifiedPrefix)
/*      */           {
/* 1528 */             return namespace; } 
/* 1529 */           if (prefix != null && prefix.equals(specifiedPrefix))
/*      */           {
/* 1531 */             return namespace;
/*      */           }
/*      */         } 
/* 1534 */         if (hasAttributes()) {
/* 1535 */           NamedNodeMap map = getAttributes();
/* 1536 */           int length = map.getLength();
/* 1537 */           for (int i = 0; i < length; i++) {
/* 1538 */             Node attr = map.item(i);
/* 1539 */             String attrPrefix = attr.getPrefix();
/* 1540 */             String value = attr.getNodeValue();
/* 1541 */             namespace = attr.getNamespaceURI();
/* 1542 */             if (namespace != null && namespace.equals("http://www.w3.org/2000/xmlns/")) {
/*      */               
/* 1544 */               if (specifiedPrefix == null && attr
/* 1545 */                 .getNodeName().equals("xmlns"))
/*      */               {
/* 1547 */                 return value; } 
/* 1548 */               if (attrPrefix != null && attrPrefix
/* 1549 */                 .equals("xmlns") && attr
/* 1550 */                 .getLocalName().equals(specifiedPrefix))
/*      */               {
/* 1552 */                 return value;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 1557 */         nodeImpl1 = (NodeImpl)getElementAncestor(this);
/* 1558 */         if (nodeImpl1 != null) {
/* 1559 */           return nodeImpl1.lookupNamespaceURI(specifiedPrefix);
/*      */         }
/*      */         
/* 1562 */         return null;
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/* 1567 */         return ((NodeImpl)((Document)this).getDocumentElement()).lookupNamespaceURI(specifiedPrefix);
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/* 1574 */         return null;
/*      */       case 2:
/* 1576 */         if (this.ownerNode.getNodeType() == 1) {
/* 1577 */           return this.ownerNode.lookupNamespaceURI(specifiedPrefix);
/*      */         }
/*      */         
/* 1580 */         return null;
/*      */     } 
/*      */     
/* 1583 */     NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
/* 1584 */     if (ancestor != null) {
/* 1585 */       return ancestor.lookupNamespaceURI(specifiedPrefix);
/*      */     }
/* 1587 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Node getElementAncestor(Node currentNode) {
/* 1595 */     Node parent = currentNode.getParentNode();
/* 1596 */     if (parent != null) {
/* 1597 */       short type = parent.getNodeType();
/* 1598 */       if (type == 1) {
/* 1599 */         return parent;
/*      */       }
/* 1601 */       return getElementAncestor(parent);
/*      */     } 
/* 1603 */     return null;
/*      */   }
/*      */   
/*      */   String lookupNamespacePrefix(String namespaceURI, ElementImpl el) {
/* 1607 */     String namespace = getNamespaceURI();
/*      */ 
/*      */     
/* 1610 */     String prefix = getPrefix();
/*      */     
/* 1612 */     if (namespace != null && namespace.equals(namespaceURI) && 
/* 1613 */       prefix != null) {
/* 1614 */       String foundNamespace = el.lookupNamespaceURI(prefix);
/* 1615 */       if (foundNamespace != null && foundNamespace.equals(namespaceURI)) {
/* 1616 */         return prefix;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1621 */     if (hasAttributes()) {
/* 1622 */       NamedNodeMap map = getAttributes();
/* 1623 */       int length = map.getLength();
/* 1624 */       for (int i = 0; i < length; i++) {
/* 1625 */         Node attr = map.item(i);
/* 1626 */         String attrPrefix = attr.getPrefix();
/* 1627 */         String value = attr.getNodeValue();
/* 1628 */         namespace = attr.getNamespaceURI();
/* 1629 */         if (namespace != null && namespace.equals("http://www.w3.org/2000/xmlns/"))
/*      */         {
/* 1631 */           if (attr.getNodeName().equals("xmlns") || (attrPrefix != null && attrPrefix
/* 1632 */             .equals("xmlns") && value
/* 1633 */             .equals(namespaceURI))) {
/*      */             
/* 1635 */             String localname = attr.getLocalName();
/* 1636 */             String foundNamespace = el.lookupNamespaceURI(localname);
/* 1637 */             if (foundNamespace != null && foundNamespace.equals(namespaceURI)) {
/* 1638 */               return localname;
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1646 */     NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
/*      */     
/* 1648 */     if (ancestor != null) {
/* 1649 */       return ancestor.lookupNamespacePrefix(namespaceURI, el);
/*      */     }
/* 1651 */     return null;
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
/*      */   public boolean isEqualNode(Node arg) {
/* 1697 */     if (arg == this) {
/* 1698 */       return true;
/*      */     }
/* 1700 */     if (arg.getNodeType() != getNodeType()) {
/* 1701 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1705 */     if (getNodeName() == null) {
/* 1706 */       if (arg.getNodeName() != null) {
/* 1707 */         return false;
/*      */       }
/*      */     }
/* 1710 */     else if (!getNodeName().equals(arg.getNodeName())) {
/* 1711 */       return false;
/*      */     } 
/*      */     
/* 1714 */     if (getLocalName() == null) {
/* 1715 */       if (arg.getLocalName() != null) {
/* 1716 */         return false;
/*      */       }
/*      */     }
/* 1719 */     else if (!getLocalName().equals(arg.getLocalName())) {
/* 1720 */       return false;
/*      */     } 
/*      */     
/* 1723 */     if (getNamespaceURI() == null) {
/* 1724 */       if (arg.getNamespaceURI() != null) {
/* 1725 */         return false;
/*      */       }
/*      */     }
/* 1728 */     else if (!getNamespaceURI().equals(arg.getNamespaceURI())) {
/* 1729 */       return false;
/*      */     } 
/*      */     
/* 1732 */     if (getPrefix() == null) {
/* 1733 */       if (arg.getPrefix() != null) {
/* 1734 */         return false;
/*      */       }
/*      */     }
/* 1737 */     else if (!getPrefix().equals(arg.getPrefix())) {
/* 1738 */       return false;
/*      */     } 
/*      */     
/* 1741 */     if (getNodeValue() == null) {
/* 1742 */       if (arg.getNodeValue() != null) {
/* 1743 */         return false;
/*      */       }
/*      */     }
/* 1746 */     else if (!getNodeValue().equals(arg.getNodeValue())) {
/* 1747 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1751 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getFeature(String feature, String version) {
/* 1760 */     return isSupported(feature, version) ? this : null;
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
/*      */   public Object setUserData(String key, Object data, UserDataHandler handler) {
/* 1779 */     return ownerDocument().setUserData(this, key, data, handler);
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
/*      */   public Object getUserData(String key) {
/* 1792 */     return ownerDocument().getUserData(this, key);
/*      */   }
/*      */   
/*      */   protected Map<String, ParentNode.UserDataRecord> getUserDataRecord() {
/* 1796 */     return ownerDocument().getUserDataRecord(this);
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
/*      */   public void setReadOnly(boolean readOnly, boolean deep) {
/* 1823 */     if (needsSyncData()) {
/* 1824 */       synchronizeData();
/*      */     }
/* 1826 */     isReadOnly(readOnly);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getReadOnly() {
/* 1836 */     if (needsSyncData()) {
/* 1837 */       synchronizeData();
/*      */     }
/* 1839 */     return isReadOnly();
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
/*      */   public void setUserData(Object data) {
/* 1856 */     ownerDocument().setUserData(this, data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getUserData() {
/* 1864 */     return ownerDocument().getUserData(this);
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
/*      */   protected void changed() {
/* 1878 */     ownerDocument().changed();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int changes() {
/* 1888 */     return ownerDocument().changes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void synchronizeData() {
/* 1897 */     needsSyncData(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Node getContainer() {
/* 1905 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean isReadOnly() {
/* 1914 */     return ((this.flags & 0x1) != 0);
/*      */   }
/*      */   
/*      */   final void isReadOnly(boolean value) {
/* 1918 */     this.flags = (short)(value ? (this.flags | 0x1) : (this.flags & 0xFFFFFFFE));
/*      */   }
/*      */   
/*      */   final boolean needsSyncData() {
/* 1922 */     return ((this.flags & 0x2) != 0);
/*      */   }
/*      */   
/*      */   final void needsSyncData(boolean value) {
/* 1926 */     this.flags = (short)(value ? (this.flags | 0x2) : (this.flags & 0xFFFFFFFD));
/*      */   }
/*      */   
/*      */   final boolean needsSyncChildren() {
/* 1930 */     return ((this.flags & 0x4) != 0);
/*      */   }
/*      */   
/*      */   public final void needsSyncChildren(boolean value) {
/* 1934 */     this.flags = (short)(value ? (this.flags | 0x4) : (this.flags & 0xFFFFFFFB));
/*      */   }
/*      */   
/*      */   final boolean isOwned() {
/* 1938 */     return ((this.flags & 0x8) != 0);
/*      */   }
/*      */   
/*      */   final void isOwned(boolean value) {
/* 1942 */     this.flags = (short)(value ? (this.flags | 0x8) : (this.flags & 0xFFFFFFF7));
/*      */   }
/*      */   
/*      */   final boolean isFirstChild() {
/* 1946 */     return ((this.flags & 0x10) != 0);
/*      */   }
/*      */   
/*      */   final void isFirstChild(boolean value) {
/* 1950 */     this.flags = (short)(value ? (this.flags | 0x10) : (this.flags & 0xFFFFFFEF));
/*      */   }
/*      */   
/*      */   final boolean isSpecified() {
/* 1954 */     return ((this.flags & 0x20) != 0);
/*      */   }
/*      */   
/*      */   final void isSpecified(boolean value) {
/* 1958 */     this.flags = (short)(value ? (this.flags | 0x20) : (this.flags & 0xFFFFFFDF));
/*      */   }
/*      */ 
/*      */   
/*      */   final boolean internalIsIgnorableWhitespace() {
/* 1963 */     return ((this.flags & 0x40) != 0);
/*      */   }
/*      */   
/*      */   final void isIgnorableWhitespace(boolean value) {
/* 1967 */     this.flags = (short)(value ? (this.flags | 0x40) : (this.flags & 0xFFFFFFBF));
/*      */   }
/*      */   
/*      */   final boolean hasStringValue() {
/* 1971 */     return ((this.flags & 0x80) != 0);
/*      */   }
/*      */   
/*      */   final void hasStringValue(boolean value) {
/* 1975 */     this.flags = (short)(value ? (this.flags | 0x80) : (this.flags & 0xFFFFFF7F));
/*      */   }
/*      */   
/*      */   final boolean isNormalized() {
/* 1979 */     return ((this.flags & 0x100) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   final void isNormalized(boolean value) {
/* 1984 */     if (!value && isNormalized() && this.ownerNode != null) {
/* 1985 */       this.ownerNode.isNormalized(false);
/*      */     }
/* 1987 */     this.flags = (short)(value ? (this.flags | 0x100) : (this.flags & 0xFFFFFEFF));
/*      */   }
/*      */   
/*      */   final boolean isIdAttribute() {
/* 1991 */     return ((this.flags & 0x200) != 0);
/*      */   }
/*      */   
/*      */   final void isIdAttribute(boolean value) {
/* 1995 */     this.flags = (short)(value ? (this.flags | 0x200) : (this.flags & 0xFFFFFDFF));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2004 */     return "[" + getNodeName() + ": " + getNodeValue() + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 2015 */     if (needsSyncData()) {
/* 2016 */       synchronizeData();
/*      */     }
/*      */     
/* 2019 */     out.defaultWriteObject();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/NodeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */