/*    */ package com.sun.xml.internal.fastinfoset.stax.events;
/*    */ 
/*    */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*    */ import java.util.Iterator;
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
/*    */ public class EmptyIterator
/*    */   implements Iterator
/*    */ {
/* 35 */   public static final EmptyIterator instance = new EmptyIterator();
/*    */ 
/*    */ 
/*    */   
/*    */   public static EmptyIterator getInstance() {
/* 40 */     return instance;
/*    */   }
/*    */   public boolean hasNext() {
/* 43 */     return false;
/*    */   }
/*    */   public Object next() throws NoSuchElementException {
/* 46 */     throw new NoSuchElementException();
/*    */   }
/*    */   public void remove() {
/* 49 */     throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.emptyIterator"));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/EmptyIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */