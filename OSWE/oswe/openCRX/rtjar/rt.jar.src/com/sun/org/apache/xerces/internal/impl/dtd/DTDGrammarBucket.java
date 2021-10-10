/*     */ package com.sun.org.apache.xerces.internal.impl.dtd;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTDGrammarBucket
/*     */ {
/*  65 */   protected Map<XMLDTDDescription, DTDGrammar> fGrammars = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   protected DTDGrammar fActiveGrammar;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean fIsStandalone;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putGrammar(DTDGrammar grammar) {
/*  79 */     XMLDTDDescription desc = (XMLDTDDescription)grammar.getGrammarDescription();
/*  80 */     this.fGrammars.put(desc, grammar);
/*     */   }
/*     */ 
/*     */   
/*     */   public DTDGrammar getGrammar(XMLGrammarDescription desc) {
/*  85 */     return this.fGrammars.get(desc);
/*     */   }
/*     */   
/*     */   public void clear() {
/*  89 */     this.fGrammars.clear();
/*  90 */     this.fActiveGrammar = null;
/*  91 */     this.fIsStandalone = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setStandalone(boolean standalone) {
/*  98 */     this.fIsStandalone = standalone;
/*     */   }
/*     */   
/*     */   boolean getStandalone() {
/* 102 */     return this.fIsStandalone;
/*     */   }
/*     */ 
/*     */   
/*     */   void setActiveGrammar(DTDGrammar grammar) {
/* 107 */     this.fActiveGrammar = grammar;
/*     */   }
/*     */   DTDGrammar getActiveGrammar() {
/* 110 */     return this.fActiveGrammar;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dtd/DTDGrammarBucket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */