/*    */ package com.sun.xml.internal.fastinfoset.stax.events;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*    */ import java.util.Iterator;
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
/*    */ public class ReadIterator
/*    */   implements Iterator
/*    */ {
/* 35 */   Iterator iterator = EmptyIterator.getInstance();
/*    */ 
/*    */   
/*    */   public ReadIterator() {}
/*    */   
/*    */   public ReadIterator(Iterator iterator) {
/* 41 */     if (iterator != null) {
/* 42 */       this.iterator = iterator;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 47 */     return this.iterator.hasNext();
/*    */   }
/*    */   
/*    */   public Object next() {
/* 51 */     return this.iterator.next();
/*    */   }
/*    */   
/*    */   public void remove() {
/* 55 */     throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.readonlyList"));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/ReadIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */