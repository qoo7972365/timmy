/*    */ package com.sun.org.apache.regexp.internal;
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
/*    */ public final class StringCharacterIterator
/*    */   implements CharacterIterator
/*    */ {
/*    */   private final String src;
/*    */   
/*    */   public StringCharacterIterator(String src) {
/* 36 */     this.src = src;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String substring(int beginIndex, int endIndex) {
/* 42 */     return this.src.substring(beginIndex, endIndex);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String substring(int beginIndex) {
/* 48 */     return this.src.substring(beginIndex);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public char charAt(int pos) {
/* 54 */     return this.src.charAt(pos);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnd(int pos) {
/* 60 */     return (pos >= this.src.length());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/regexp/internal/StringCharacterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */