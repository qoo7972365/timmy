/*    */ package com.sun.xml.internal.bind.v2.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.NoSuchElementException;
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
/*    */ 
/*    */ 
/*    */ public final class FlattenIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/*    */   private final Iterator<? extends Map<?, ? extends T>> parent;
/* 41 */   private Iterator<? extends T> child = null;
/*    */   private T next;
/*    */   
/*    */   public FlattenIterator(Iterable<? extends Map<?, ? extends T>> core) {
/* 45 */     this.parent = core.iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 50 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 54 */     getNext();
/* 55 */     return (this.next != null);
/*    */   }
/*    */   
/*    */   public T next() {
/* 59 */     T r = this.next;
/* 60 */     this.next = null;
/* 61 */     if (r == null)
/* 62 */       throw new NoSuchElementException(); 
/* 63 */     return r;
/*    */   }
/*    */   
/*    */   private void getNext() {
/* 67 */     if (this.next != null)
/*    */       return; 
/* 69 */     if (this.child != null && this.child.hasNext()) {
/* 70 */       this.next = this.child.next();
/*    */       
/*    */       return;
/*    */     } 
/* 74 */     if (this.parent.hasNext()) {
/* 75 */       this.child = ((Map)this.parent.next()).values().iterator();
/* 76 */       getNext();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/util/FlattenIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */