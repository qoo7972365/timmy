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
/*    */ final class SimpleXMLSchema
/*    */   extends AbstractXMLSchema
/*    */   implements XMLGrammarPool
/*    */ {
/* 36 */   private static final Grammar[] ZERO_LENGTH_GRAMMAR_ARRAY = new Grammar[0];
/*    */   
/*    */   private Grammar fGrammar;
/*    */   private Grammar[] fGrammars;
/*    */   private XMLGrammarDescription fGrammarDescription;
/*    */   
/*    */   public SimpleXMLSchema(Grammar grammar) {
/* 43 */     this.fGrammar = grammar;
/* 44 */     this.fGrammars = new Grammar[] { grammar };
/* 45 */     this.fGrammarDescription = grammar.getGrammarDescription();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Grammar[] retrieveInitialGrammarSet(String grammarType) {
/* 53 */     return "http://www.w3.org/2001/XMLSchema".equals(grammarType) ? (Grammar[])this.fGrammars
/* 54 */       .clone() : ZERO_LENGTH_GRAMMAR_ARRAY;
/*    */   }
/*    */   
/*    */   public void cacheGrammars(String grammarType, Grammar[] grammars) {}
/*    */   
/*    */   public Grammar retrieveGrammar(XMLGrammarDescription desc) {
/* 60 */     return this.fGrammarDescription.equals(desc) ? this.fGrammar : null;
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
/* 74 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isFullyComposed() {
/* 78 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/SimpleXMLSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */