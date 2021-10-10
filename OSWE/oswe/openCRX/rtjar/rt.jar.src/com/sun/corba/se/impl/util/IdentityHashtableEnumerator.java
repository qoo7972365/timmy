/*    */ package com.sun.corba.se.impl.util;
/*    */ 
/*    */ import java.util.Enumeration;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class IdentityHashtableEnumerator
/*    */   implements Enumeration
/*    */ {
/*    */   boolean keys;
/*    */   int index;
/*    */   IdentityHashtableEntry[] table;
/*    */   IdentityHashtableEntry entry;
/*    */   
/*    */   IdentityHashtableEnumerator(IdentityHashtableEntry[] paramArrayOfIdentityHashtableEntry, boolean paramBoolean) {
/* 50 */     this.table = paramArrayOfIdentityHashtableEntry;
/* 51 */     this.keys = paramBoolean;
/* 52 */     this.index = paramArrayOfIdentityHashtableEntry.length;
/*    */   }
/*    */   
/*    */   public boolean hasMoreElements() {
/* 56 */     if (this.entry != null) {
/* 57 */       return true;
/*    */     }
/* 59 */     while (this.index-- > 0) {
/* 60 */       if ((this.entry = this.table[this.index]) != null) {
/* 61 */         return true;
/*    */       }
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   public Object nextElement() {
/* 68 */     if (this.entry == null) {
/* 69 */       while (this.index-- > 0 && (this.entry = this.table[this.index]) == null);
/*    */     }
/* 71 */     if (this.entry != null) {
/* 72 */       IdentityHashtableEntry identityHashtableEntry = this.entry;
/* 73 */       this.entry = identityHashtableEntry.next;
/* 74 */       return this.keys ? identityHashtableEntry.key : identityHashtableEntry.value;
/*    */     } 
/* 76 */     throw new NoSuchElementException("IdentityHashtableEnumerator");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/util/IdentityHashtableEnumerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */