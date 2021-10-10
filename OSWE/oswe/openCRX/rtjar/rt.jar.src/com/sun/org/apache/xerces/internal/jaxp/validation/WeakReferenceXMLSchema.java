/*    */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*    */ import java.lang.ref.WeakReference;
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
/*    */ final class WeakReferenceXMLSchema
/*    */   extends AbstractXMLSchema
/*    */ {
/* 39 */   private WeakReference fGrammarPool = new WeakReference(null);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized XMLGrammarPool getGrammarPool() {
/* 48 */     XMLGrammarPool grammarPool = this.fGrammarPool.get();
/*    */ 
/*    */     
/* 51 */     if (grammarPool == null) {
/* 52 */       grammarPool = new SoftReferenceGrammarPool();
/* 53 */       this.fGrammarPool = new WeakReference<>(grammarPool);
/*    */     } 
/* 55 */     return grammarPool;
/*    */   }
/*    */   
/*    */   public boolean isFullyComposed() {
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/WeakReferenceXMLSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */