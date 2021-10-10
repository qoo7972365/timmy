/*     */ package com.sun.org.apache.xerces.internal.impl.xs;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
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
/*     */ public class XSGrammarBucket
/*     */ {
/*  42 */   Map<String, SchemaGrammar> fGrammarRegistry = new HashMap<>();
/*  43 */   SchemaGrammar fNoNSGrammar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SchemaGrammar getGrammar(String namespace) {
/*  52 */     if (namespace == null)
/*  53 */       return this.fNoNSGrammar; 
/*  54 */     return this.fGrammarRegistry.get(namespace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putGrammar(SchemaGrammar grammar) {
/*  65 */     if (grammar.getTargetNamespace() == null) {
/*  66 */       this.fNoNSGrammar = grammar;
/*     */     } else {
/*  68 */       this.fGrammarRegistry.put(grammar.getTargetNamespace(), grammar);
/*     */     } 
/*     */   }
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
/*     */   public boolean putGrammar(SchemaGrammar grammar, boolean deep) {
/*  83 */     SchemaGrammar sg = getGrammar(grammar.fTargetNamespace);
/*  84 */     if (sg != null)
/*     */     {
/*  86 */       return (sg == grammar);
/*     */     }
/*     */     
/*  89 */     if (!deep) {
/*  90 */       putGrammar(grammar);
/*  91 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     Vector currGrammars = grammar.getImportedGrammars();
/*  98 */     if (currGrammars == null) {
/*  99 */       putGrammar(grammar);
/* 100 */       return true;
/*     */     } 
/*     */     
/* 103 */     Vector<SchemaGrammar> grammars = (Vector)currGrammars.clone();
/*     */     
/*     */     int i;
/*     */     
/* 107 */     for (i = 0; i < grammars.size(); i++) {
/*     */       
/* 109 */       SchemaGrammar sg1 = grammars.elementAt(i);
/*     */       
/* 111 */       SchemaGrammar sg2 = getGrammar(sg1.fTargetNamespace);
/* 112 */       if (sg2 == null) {
/*     */         
/* 114 */         Vector<SchemaGrammar> gs = sg1.getImportedGrammars();
/*     */ 
/*     */         
/* 117 */         if (gs != null) {
/* 118 */           for (int j = gs.size() - 1; j >= 0; j--) {
/* 119 */             sg2 = gs.elementAt(j);
/* 120 */             if (!grammars.contains(sg2)) {
/* 121 */               grammars.addElement(sg2);
/*     */             }
/*     */           }
/*     */         
/*     */         }
/* 126 */       } else if (sg2 != sg1) {
/* 127 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 132 */     putGrammar(grammar);
/* 133 */     for (i = grammars.size() - 1; i >= 0; i--) {
/* 134 */       putGrammar(grammars.elementAt(i));
/*     */     }
/* 136 */     return true;
/*     */   }
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
/*     */   public boolean putGrammar(SchemaGrammar grammar, boolean deep, boolean ignoreConflict) {
/* 152 */     if (!ignoreConflict) {
/* 153 */       return putGrammar(grammar, deep);
/*     */     }
/*     */ 
/*     */     
/* 157 */     SchemaGrammar sg = getGrammar(grammar.fTargetNamespace);
/* 158 */     if (sg == null) {
/* 159 */       putGrammar(grammar);
/*     */     }
/*     */ 
/*     */     
/* 163 */     if (!deep) {
/* 164 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     Vector currGrammars = grammar.getImportedGrammars();
/* 171 */     if (currGrammars == null) {
/* 172 */       return true;
/*     */     }
/*     */     
/* 175 */     Vector<SchemaGrammar> grammars = (Vector)currGrammars.clone();
/*     */     
/*     */     int i;
/*     */     
/* 179 */     for (i = 0; i < grammars.size(); i++) {
/*     */       
/* 181 */       SchemaGrammar sg1 = grammars.elementAt(i);
/*     */       
/* 183 */       SchemaGrammar sg2 = getGrammar(sg1.fTargetNamespace);
/* 184 */       if (sg2 == null) {
/*     */         
/* 186 */         Vector<SchemaGrammar> gs = sg1.getImportedGrammars();
/*     */ 
/*     */         
/* 189 */         if (gs != null) {
/* 190 */           for (int j = gs.size() - 1; j >= 0; j--) {
/* 191 */             sg2 = gs.elementAt(j);
/* 192 */             if (!grammars.contains(sg2)) {
/* 193 */               grammars.addElement(sg2);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } else {
/* 198 */         grammars.remove(sg1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 203 */     for (i = grammars.size() - 1; i >= 0; i--) {
/* 204 */       putGrammar(grammars.elementAt(i));
/*     */     }
/*     */     
/* 207 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SchemaGrammar[] getGrammars() {
/* 217 */     int count = this.fGrammarRegistry.size() + ((this.fNoNSGrammar == null) ? 0 : 1);
/* 218 */     SchemaGrammar[] grammars = new SchemaGrammar[count];
/*     */     
/* 220 */     int i = 0;
/* 221 */     for (Map.Entry<String, SchemaGrammar> entry : this.fGrammarRegistry.entrySet()) {
/* 222 */       grammars[i++] = entry.getValue();
/*     */     }
/*     */ 
/*     */     
/* 226 */     if (this.fNoNSGrammar != null)
/* 227 */       grammars[count - 1] = this.fNoNSGrammar; 
/* 228 */     return grammars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 236 */     this.fNoNSGrammar = null;
/* 237 */     this.fGrammarRegistry.clear();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/XSGrammarBucket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */