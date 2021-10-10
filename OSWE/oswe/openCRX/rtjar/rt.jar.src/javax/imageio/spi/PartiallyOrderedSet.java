/*     */ package javax.imageio.spi;
/*     */ 
/*     */ import java.util.AbstractSet;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PartiallyOrderedSet
/*     */   extends AbstractSet
/*     */ {
/*  61 */   private Map poNodes = new HashMap<>();
/*     */ 
/*     */   
/*  64 */   private Set nodes = this.poNodes.keySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  72 */     return this.nodes.size();
/*     */   }
/*     */   
/*     */   public boolean contains(Object paramObject) {
/*  76 */     return this.nodes.contains(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/*  85 */     return new PartialOrderIterator(this.poNodes.values().iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Object paramObject) {
/*  93 */     if (this.nodes.contains(paramObject)) {
/*  94 */       return false;
/*     */     }
/*     */     
/*  97 */     DigraphNode digraphNode = new DigraphNode(paramObject);
/*  98 */     this.poNodes.put(paramObject, digraphNode);
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object paramObject) {
/* 107 */     DigraphNode digraphNode = (DigraphNode)this.poNodes.get(paramObject);
/* 108 */     if (digraphNode == null) {
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     this.poNodes.remove(paramObject);
/* 113 */     digraphNode.dispose();
/* 114 */     return true;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 118 */     this.poNodes.clear();
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
/*     */   public boolean setOrdering(Object paramObject1, Object paramObject2) {
/* 132 */     DigraphNode digraphNode1 = (DigraphNode)this.poNodes.get(paramObject1);
/*     */     
/* 134 */     DigraphNode digraphNode2 = (DigraphNode)this.poNodes.get(paramObject2);
/*     */     
/* 136 */     digraphNode2.removeEdge(digraphNode1);
/* 137 */     return digraphNode1.addEdge(digraphNode2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unsetOrdering(Object paramObject1, Object paramObject2) {
/* 147 */     DigraphNode digraphNode1 = (DigraphNode)this.poNodes.get(paramObject1);
/*     */     
/* 149 */     DigraphNode digraphNode2 = (DigraphNode)this.poNodes.get(paramObject2);
/*     */     
/* 151 */     return (digraphNode1.removeEdge(digraphNode2) || digraphNode2
/* 152 */       .removeEdge(digraphNode1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasOrdering(Object paramObject1, Object paramObject2) {
/* 161 */     DigraphNode digraphNode1 = (DigraphNode)this.poNodes.get(paramObject1);
/*     */     
/* 163 */     DigraphNode digraphNode2 = (DigraphNode)this.poNodes.get(paramObject2);
/*     */     
/* 165 */     return digraphNode1.hasEdge(digraphNode2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/spi/PartiallyOrderedSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */