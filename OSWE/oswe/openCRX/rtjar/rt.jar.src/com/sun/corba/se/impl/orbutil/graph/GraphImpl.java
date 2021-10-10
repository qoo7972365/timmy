/*     */ package com.sun.corba.se.impl.orbutil.graph;
/*     */ 
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GraphImpl
/*     */   extends AbstractSet
/*     */   implements Graph
/*     */ {
/*  42 */   private Map nodeToData = new HashMap<>();
/*     */   
/*     */   public GraphImpl() {}
/*     */   
/*     */   public GraphImpl(Collection<? extends E> paramCollection) {
/*  47 */     this();
/*  48 */     addAll(paramCollection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Object paramObject) {
/*  58 */     if (!(paramObject instanceof Node)) {
/*  59 */       throw new IllegalArgumentException("Graphs must contain only Node instances");
/*     */     }
/*  61 */     Node node = (Node)paramObject;
/*  62 */     boolean bool = this.nodeToData.keySet().contains(paramObject);
/*     */     
/*  64 */     if (!bool) {
/*  65 */       NodeData nodeData = new NodeData();
/*  66 */       this.nodeToData.put(node, nodeData);
/*     */     } 
/*     */     
/*  69 */     return !bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/*  75 */     return this.nodeToData.keySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  81 */     return this.nodeToData.keySet().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeData getNodeData(Node paramNode) {
/*  88 */     return (NodeData)this.nodeToData.get(paramNode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void clearNodeData() {
/*  94 */     Iterator<Map.Entry> iterator = this.nodeToData.entrySet().iterator();
/*  95 */     while (iterator.hasNext()) {
/*  96 */       Map.Entry entry = iterator.next();
/*  97 */       NodeData nodeData = (NodeData)entry.getValue();
/*  98 */       nodeData.clear();
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
/*     */   void visitAll(NodeVisitor paramNodeVisitor) {
/* 112 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 118 */       bool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       Map.Entry[] arrayOfEntry = (Map.Entry[])this.nodeToData.entrySet().toArray((Object[])new Map.Entry[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       for (byte b = 0; b < arrayOfEntry.length; b++) {
/* 129 */         Map.Entry entry = arrayOfEntry[b];
/* 130 */         Node node = (Node)entry.getKey();
/* 131 */         NodeData nodeData = (NodeData)entry.getValue();
/*     */         
/* 133 */         if (!nodeData.isVisited()) {
/* 134 */           nodeData.visited();
/* 135 */           bool = false;
/*     */           
/* 137 */           paramNodeVisitor.visit(this, node, nodeData);
/*     */         } 
/*     */       } 
/* 140 */     } while (!bool);
/*     */   }
/*     */ 
/*     */   
/*     */   private void markNonRoots() {
/* 145 */     visitAll(new NodeVisitor()
/*     */         {
/*     */           public void visit(Graph param1Graph, Node param1Node, NodeData param1NodeData)
/*     */           {
/* 149 */             Iterator<Node> iterator = param1Node.getChildren().iterator();
/* 150 */             while (iterator.hasNext()) {
/* 151 */               Node node = iterator.next();
/*     */ 
/*     */ 
/*     */               
/* 155 */               param1Graph.add((E)node);
/*     */ 
/*     */               
/* 158 */               NodeData nodeData = param1Graph.getNodeData(node);
/* 159 */               nodeData.notRoot();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private Set collectRootSet() {
/* 167 */     HashSet<Node> hashSet = new HashSet();
/*     */     
/* 169 */     Iterator<Map.Entry> iterator = this.nodeToData.entrySet().iterator();
/* 170 */     while (iterator.hasNext()) {
/* 171 */       Map.Entry entry = iterator.next();
/* 172 */       Node node = (Node)entry.getKey();
/* 173 */       NodeData nodeData = (NodeData)entry.getValue();
/* 174 */       if (nodeData.isRoot()) {
/* 175 */         hashSet.add(node);
/*     */       }
/*     */     } 
/* 178 */     return hashSet;
/*     */   }
/*     */   static interface NodeVisitor {
/*     */     void visit(Graph param1Graph, Node param1Node, NodeData param1NodeData); }
/*     */   public Set getRoots() {
/* 183 */     clearNodeData();
/* 184 */     markNonRoots();
/* 185 */     return collectRootSet();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/graph/GraphImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */