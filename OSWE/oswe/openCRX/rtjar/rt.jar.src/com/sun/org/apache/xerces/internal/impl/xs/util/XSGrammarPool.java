/*    */ package com.sun.org.apache.xerces.internal.impl.xs.util;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaGrammar;
/*    */ import com.sun.org.apache.xerces.internal.impl.xs.XSModelImpl;
/*    */ import com.sun.org.apache.xerces.internal.util.XMLGrammarPoolImpl;
/*    */ import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
/*    */ import com.sun.org.apache.xerces.internal.xs.XSModel;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ public class XSGrammarPool
/*    */   extends XMLGrammarPoolImpl
/*    */ {
/*    */   public XSModel toXSModel() {
/* 50 */     return toXSModel((short)1);
/*    */   }
/*    */   
/*    */   public XSModel toXSModel(short schemaVersion) {
/* 54 */     ArrayList<Grammar> list = new ArrayList();
/* 55 */     for (int i = 0; i < this.fGrammars.length; i++) {
/* 56 */       for (XMLGrammarPoolImpl.Entry entry = this.fGrammars[i]; entry != null; entry = entry.next) {
/* 57 */         if (entry.desc.getGrammarType().equals("http://www.w3.org/2001/XMLSchema")) {
/* 58 */           list.add(entry.grammar);
/*    */         }
/*    */       } 
/*    */     } 
/* 62 */     int size = list.size();
/* 63 */     if (size == 0) {
/* 64 */       return toXSModel(new SchemaGrammar[0], schemaVersion);
/*    */     }
/* 66 */     SchemaGrammar[] gs = list.<SchemaGrammar>toArray(new SchemaGrammar[size]);
/* 67 */     return toXSModel(gs, schemaVersion);
/*    */   }
/*    */   
/*    */   protected XSModel toXSModel(SchemaGrammar[] grammars, short schemaVersion) {
/* 71 */     return new XSModelImpl(grammars, schemaVersion);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/util/XSGrammarPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */