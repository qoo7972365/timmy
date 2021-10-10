/*     */ package javax.imageio.spi;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DigraphNode
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   protected Object data;
/*  51 */   protected Set outNodes = new HashSet();
/*     */ 
/*     */   
/*  54 */   protected int inDegree = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private Set inNodes = new HashSet();
/*     */   
/*     */   public DigraphNode(Object paramObject) {
/*  63 */     this.data = paramObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getData() {
/*  68 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getOutNodes() {
/*  76 */     return this.outNodes.iterator();
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
/*     */   public boolean addEdge(DigraphNode paramDigraphNode) {
/*  89 */     if (this.outNodes.contains(paramDigraphNode)) {
/*  90 */       return false;
/*     */     }
/*     */     
/*  93 */     this.outNodes.add(paramDigraphNode);
/*  94 */     paramDigraphNode.inNodes.add(this);
/*  95 */     paramDigraphNode.incrementInDegree();
/*  96 */     return true;
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
/*     */   public boolean hasEdge(DigraphNode paramDigraphNode) {
/* 108 */     return this.outNodes.contains(paramDigraphNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeEdge(DigraphNode paramDigraphNode) {
/* 119 */     if (!this.outNodes.contains(paramDigraphNode)) {
/* 120 */       return false;
/*     */     }
/*     */     
/* 123 */     this.outNodes.remove(paramDigraphNode);
/* 124 */     paramDigraphNode.inNodes.remove(this);
/* 125 */     paramDigraphNode.decrementInDegree();
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 134 */     Object[] arrayOfObject1 = this.inNodes.toArray();
/* 135 */     for (byte b1 = 0; b1 < arrayOfObject1.length; b1++) {
/* 136 */       DigraphNode digraphNode = (DigraphNode)arrayOfObject1[b1];
/* 137 */       digraphNode.removeEdge(this);
/*     */     } 
/*     */     
/* 140 */     Object[] arrayOfObject2 = this.outNodes.toArray();
/* 141 */     for (byte b2 = 0; b2 < arrayOfObject2.length; b2++) {
/* 142 */       DigraphNode digraphNode = (DigraphNode)arrayOfObject2[b2];
/* 143 */       removeEdge(digraphNode);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInDegree() {
/* 149 */     return this.inDegree;
/*     */   }
/*     */ 
/*     */   
/*     */   private void incrementInDegree() {
/* 154 */     this.inDegree++;
/*     */   }
/*     */ 
/*     */   
/*     */   private void decrementInDegree() {
/* 159 */     this.inDegree--;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/spi/DigraphNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */