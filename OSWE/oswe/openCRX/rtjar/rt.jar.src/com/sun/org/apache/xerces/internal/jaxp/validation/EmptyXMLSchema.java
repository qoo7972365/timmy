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
/*    */ 
/*    */ final class EmptyXMLSchema
/*    */   extends AbstractXMLSchema
/*    */   implements XMLGrammarPool
/*    */ {
/* 37 */   private static final Grammar[] ZERO_LENGTH_GRAMMAR_ARRAY = new Grammar[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Grammar[] retrieveInitialGrammarSet(String grammarType) {
/* 46 */     return ZERO_LENGTH_GRAMMAR_ARRAY;
/*    */   }
/*    */   
/*    */   public void cacheGrammars(String grammarType, Grammar[] grammars) {}
/*    */   
/*    */   public Grammar retrieveGrammar(XMLGrammarDescription desc) {
/* 52 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void lockPool() {}
/*    */ 
/*    */   
/*    */   public void unlockPool() {}
/*    */ 
/*    */   
/*    */   public void clear() {}
/*    */ 
/*    */   
/*    */   public XMLGrammarPool getGrammarPool() {
/* 66 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isFullyComposed() {
/* 70 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/EmptyXMLSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */