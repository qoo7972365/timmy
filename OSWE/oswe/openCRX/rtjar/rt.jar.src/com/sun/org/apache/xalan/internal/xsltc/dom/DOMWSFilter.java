/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOMEnhancedForDTM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.StripFilter;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
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
/*     */ public class DOMWSFilter
/*     */   implements DTMWSFilter
/*     */ {
/*     */   private AbstractTranslet m_translet;
/*     */   private StripFilter m_filter;
/*     */   private Map<DTM, short[]> m_mappings;
/*     */   private DTM m_currentDTM;
/*     */   private short[] m_currentMapping;
/*     */   
/*     */   public DOMWSFilter(AbstractTranslet translet) {
/*  62 */     this.m_translet = translet;
/*  63 */     this.m_mappings = (Map)new HashMap<>();
/*     */     
/*  65 */     if (translet instanceof StripFilter) {
/*  66 */       this.m_filter = (StripFilter)translet;
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
/*     */ 
/*     */   
/*     */   public short getShouldStripSpace(int node, DTM dtm) {
/*  83 */     if (this.m_filter != null && dtm instanceof DOM) {
/*  84 */       DOM dom = (DOM)dtm;
/*  85 */       int type = 0;
/*     */       
/*  87 */       if (dtm instanceof DOMEnhancedForDTM) {
/*  88 */         short[] mapping; DOMEnhancedForDTM mappableDOM = (DOMEnhancedForDTM)dtm;
/*     */ 
/*     */         
/*  91 */         if (dtm == this.m_currentDTM) {
/*  92 */           mapping = this.m_currentMapping;
/*     */         } else {
/*     */           
/*  95 */           mapping = this.m_mappings.get(dtm);
/*  96 */           if (mapping == null) {
/*  97 */             mapping = mappableDOM.getMapping(this.m_translet
/*  98 */                 .getNamesArray(), this.m_translet
/*  99 */                 .getUrisArray(), this.m_translet
/* 100 */                 .getTypesArray());
/* 101 */             this.m_mappings.put(dtm, mapping);
/* 102 */             this.m_currentDTM = dtm;
/* 103 */             this.m_currentMapping = mapping;
/*     */           } 
/*     */         } 
/*     */         
/* 107 */         int expType = mappableDOM.getExpandedTypeID(node);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 114 */         if (expType >= 0 && expType < mapping.length) {
/* 115 */           type = mapping[expType];
/*     */         } else {
/* 117 */           type = -1;
/*     */         } 
/*     */       } else {
/*     */         
/* 121 */         return 3;
/*     */       } 
/*     */       
/* 124 */       if (this.m_filter.stripSpace(dom, node, type)) {
/* 125 */         return 2;
/*     */       }
/* 127 */       return 1;
/*     */     } 
/*     */     
/* 130 */     return 1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/DOMWSFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */