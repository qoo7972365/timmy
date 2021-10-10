/*    */ package com.sun.xml.internal.ws.util.xml;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import org.w3c.dom.NodeList;
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
/*    */ public class NodeListIterator
/*    */   implements Iterator
/*    */ {
/*    */   protected NodeList _list;
/*    */   protected int _index;
/*    */   
/*    */   public NodeListIterator(NodeList list) {
/* 41 */     this._list = list;
/* 42 */     this._index = 0;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 46 */     if (this._list == null)
/* 47 */       return false; 
/* 48 */     return (this._index < this._list.getLength());
/*    */   }
/*    */   
/*    */   public Object next() {
/* 52 */     Object obj = this._list.item(this._index);
/* 53 */     if (obj != null)
/* 54 */       this._index++; 
/* 55 */     return obj;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 59 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/NodeListIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */