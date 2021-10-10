/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.crypto.NodeSetData;
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
/*     */ public class DOMSubTreeData
/*     */   implements NodeSetData
/*     */ {
/*     */   private boolean excludeComments;
/*     */   private Node root;
/*     */   
/*     */   public DOMSubTreeData(Node paramNode, boolean paramBoolean) {
/*  53 */     this.root = paramNode;
/*  54 */     this.excludeComments = paramBoolean;
/*     */   }
/*     */   
/*     */   public Iterator iterator() {
/*  58 */     return new DelayedNodeIterator(this.root, this.excludeComments);
/*     */   }
/*     */   
/*     */   public Node getRoot() {
/*  62 */     return this.root;
/*     */   }
/*     */   
/*     */   public boolean excludeComments() {
/*  66 */     return this.excludeComments;
/*     */   }
/*     */ 
/*     */   
/*     */   static class DelayedNodeIterator
/*     */     implements Iterator<Node>
/*     */   {
/*     */     private Node root;
/*     */     
/*     */     private List<Node> nodeSet;
/*     */     private ListIterator<Node> li;
/*     */     private boolean withComments;
/*     */     
/*     */     DelayedNodeIterator(Node param1Node, boolean param1Boolean) {
/*  80 */       this.root = param1Node;
/*  81 */       this.withComments = !param1Boolean;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/*  85 */       if (this.nodeSet == null) {
/*  86 */         this.nodeSet = dereferenceSameDocumentURI(this.root);
/*  87 */         this.li = this.nodeSet.listIterator();
/*     */       } 
/*  89 */       return this.li.hasNext();
/*     */     }
/*     */     
/*     */     public Node next() {
/*  93 */       if (this.nodeSet == null) {
/*  94 */         this.nodeSet = dereferenceSameDocumentURI(this.root);
/*  95 */         this.li = this.nodeSet.listIterator();
/*     */       } 
/*  97 */       if (this.li.hasNext()) {
/*  98 */         return this.li.next();
/*     */       }
/* 100 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 105 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private List<Node> dereferenceSameDocumentURI(Node param1Node) {
/* 116 */       ArrayList<Node> arrayList = new ArrayList();
/* 117 */       if (param1Node != null) {
/* 118 */         nodeSetMinusCommentNodes(param1Node, arrayList, null);
/*     */       }
/* 120 */       return arrayList;
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
/*     */     private void nodeSetMinusCommentNodes(Node param1Node1, List<Node> param1List, Node param1Node2) {
/*     */       NamedNodeMap namedNodeMap;
/*     */       Node node1;
/*     */       Node node2;
/* 136 */       switch (param1Node1.getNodeType()) {
/*     */         case 1:
/* 138 */           namedNodeMap = param1Node1.getAttributes();
/* 139 */           if (namedNodeMap != null) {
/* 140 */             byte b; int i; for (b = 0, i = namedNodeMap.getLength(); b < i; b++) {
/* 141 */               param1List.add(namedNodeMap.item(b));
/*     */             }
/*     */           } 
/* 144 */           param1List.add(param1Node1);
/* 145 */           node1 = null;
/* 146 */           for (node2 = param1Node1.getFirstChild(); node2 != null; 
/* 147 */             node2 = node2.getNextSibling()) {
/* 148 */             nodeSetMinusCommentNodes(node2, param1List, node1);
/* 149 */             node1 = node2;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 153 */           node1 = null;
/* 154 */           for (node2 = param1Node1.getFirstChild(); node2 != null; 
/* 155 */             node2 = node2.getNextSibling()) {
/* 156 */             nodeSetMinusCommentNodes(node2, param1List, node1);
/* 157 */             node1 = node2;
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/*     */         case 4:
/* 164 */           if (param1Node2 != null && (param1Node2
/* 165 */             .getNodeType() == 3 || param1Node2
/* 166 */             .getNodeType() == 4)) {
/*     */             return;
/*     */           }
/* 169 */           param1List.add(param1Node1);
/*     */           break;
/*     */         case 7:
/* 172 */           param1List.add(param1Node1);
/*     */           break;
/*     */         case 8:
/* 175 */           if (this.withComments)
/* 176 */             param1List.add(param1Node1); 
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMSubTreeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */