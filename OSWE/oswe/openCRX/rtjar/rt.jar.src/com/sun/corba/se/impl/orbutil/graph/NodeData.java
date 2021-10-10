/*    */ package com.sun.corba.se.impl.orbutil.graph;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NodeData
/*    */ {
/*    */   private boolean visited;
/*    */   private boolean root;
/*    */   
/*    */   public NodeData() {
/* 37 */     clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 42 */     this.visited = false;
/* 43 */     this.root = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isVisited() {
/* 51 */     return this.visited;
/*    */   }
/*    */ 
/*    */   
/*    */   void visited() {
/* 56 */     this.visited = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isRoot() {
/* 63 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   void notRoot() {
/* 68 */     this.root = false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/graph/NodeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */