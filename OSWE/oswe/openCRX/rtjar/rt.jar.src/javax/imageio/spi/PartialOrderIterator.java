/*     */ package javax.imageio.spi;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PartialOrderIterator
/*     */   implements Iterator
/*     */ {
/* 171 */   LinkedList zeroList = new LinkedList();
/* 172 */   Map inDegrees = new HashMap<>();
/*     */ 
/*     */   
/*     */   public PartialOrderIterator(Iterator<DigraphNode> paramIterator) {
/* 176 */     while (paramIterator.hasNext()) {
/* 177 */       DigraphNode digraphNode = paramIterator.next();
/* 178 */       int i = digraphNode.getInDegree();
/* 179 */       this.inDegrees.put(digraphNode, new Integer(i));
/*     */ 
/*     */       
/* 182 */       if (i == 0) {
/* 183 */         this.zeroList.add(digraphNode);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasNext() {
/* 189 */     return !this.zeroList.isEmpty();
/*     */   }
/*     */   
/*     */   public Object next() {
/* 193 */     DigraphNode digraphNode = this.zeroList.removeFirst();
/*     */ 
/*     */     
/* 196 */     Iterator<DigraphNode> iterator = digraphNode.getOutNodes();
/* 197 */     while (iterator.hasNext()) {
/* 198 */       DigraphNode digraphNode1 = iterator.next();
/* 199 */       int i = ((Integer)this.inDegrees.get(digraphNode1)).intValue() - 1;
/* 200 */       this.inDegrees.put(digraphNode1, new Integer(i));
/*     */ 
/*     */       
/* 203 */       if (i == 0) {
/* 204 */         this.zeroList.add(digraphNode1);
/*     */       }
/*     */     } 
/*     */     
/* 208 */     return digraphNode.getData();
/*     */   }
/*     */   
/*     */   public void remove() {
/* 212 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/spi/PartialOrderIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */