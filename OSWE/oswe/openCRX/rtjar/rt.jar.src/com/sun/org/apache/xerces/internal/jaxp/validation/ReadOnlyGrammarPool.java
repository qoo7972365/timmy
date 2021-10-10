/*    */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
/*    */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
/*    */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
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
/*    */ final class ReadOnlyGrammarPool
/*    */   implements XMLGrammarPool
/*    */ {
/*    */   private final XMLGrammarPool core;
/*    */   
/*    */   public ReadOnlyGrammarPool(XMLGrammarPool pool) {
/* 38 */     this.core = pool;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void cacheGrammars(String grammarType, Grammar[] grammars) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {}
/*    */ 
/*    */   
/*    */   public void lockPool() {}
/*    */ 
/*    */   
/*    */   public Grammar retrieveGrammar(XMLGrammarDescription desc) {
/* 54 */     return this.core.retrieveGrammar(desc);
/*    */   }
/*    */   
/*    */   public Grammar[] retrieveInitialGrammarSet(String grammarType) {
/* 58 */     return this.core.retrieveInitialGrammarSet(grammarType);
/*    */   }
/*    */   
/*    */   public void unlockPool() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/ReadOnlyGrammarPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */