/*     */ package com.sun.org.apache.xpath.internal.patterns;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMAxisTraverser;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.axes.WalkerFactory;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import javax.xml.transform.TransformerException;
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
/*     */ public class ContextMatchStepPattern
/*     */   extends StepPattern
/*     */ {
/*     */   static final long serialVersionUID = -1888092779313211942L;
/*     */   
/*     */   public ContextMatchStepPattern(int axis, int paxis) {
/*  45 */     super(-1, axis, paxis);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XObject execute(XPathContext xctxt) throws TransformerException {
/*  66 */     if (xctxt.getIteratorRoot() == xctxt.getCurrentNode()) {
/*  67 */       return getStaticScore();
/*     */     }
/*  69 */     this; return SCORE_NONE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XObject executeRelativePathPattern(XPathContext xctxt, StepPattern prevStep) throws TransformerException {
/*  92 */     XObject score = NodeTest.SCORE_NONE;
/*  93 */     int context = xctxt.getCurrentNode();
/*  94 */     DTM dtm = xctxt.getDTM(context);
/*     */     
/*  96 */     if (null != dtm) {
/*     */       
/*  98 */       int predContext = xctxt.getCurrentNode();
/*     */ 
/*     */       
/* 101 */       int axis = this.m_axis;
/*     */       
/* 103 */       boolean needToTraverseAttrs = WalkerFactory.isDownwardAxisOfMany(axis);
/* 104 */       boolean iterRootIsAttr = (dtm.getNodeType(xctxt.getIteratorRoot()) == 2);
/*     */ 
/*     */       
/* 107 */       if (11 == axis && iterRootIsAttr)
/*     */       {
/* 109 */         axis = 15;
/*     */       }
/*     */       
/* 112 */       DTMAxisTraverser traverser = dtm.getAxisTraverser(axis);
/*     */       int relative;
/* 114 */       for (relative = traverser.first(context); -1 != relative; 
/* 115 */         relative = traverser.next(context, relative)) {
/*     */ 
/*     */ 
/*     */         
/* 119 */         try { xctxt.pushCurrentNode(relative);
/*     */           
/* 121 */           score = execute(xctxt);
/*     */           
/* 123 */           if (score != NodeTest.SCORE_NONE) {
/*     */ 
/*     */ 
/*     */             
/* 127 */             if (executePredicates(xctxt, dtm, context)) {
/* 128 */               return score;
/*     */             }
/* 130 */             score = NodeTest.SCORE_NONE;
/*     */           } 
/*     */           
/* 133 */           if (needToTraverseAttrs && iterRootIsAttr && 1 == dtm
/* 134 */             .getNodeType(relative)) {
/*     */             
/* 136 */             int xaxis = 2;
/* 137 */             for (int i = 0; i < 2; i++) {
/*     */               
/* 139 */               DTMAxisTraverser atraverser = dtm.getAxisTraverser(xaxis);
/*     */               
/* 141 */               int arelative = atraverser.first(relative);
/* 142 */               for (; -1 != arelative; 
/* 143 */                 arelative = atraverser.next(relative, arelative)) {
/*     */ 
/*     */ 
/*     */                 
/* 147 */                 try { xctxt.pushCurrentNode(arelative);
/*     */                   
/* 149 */                   score = execute(xctxt);
/*     */                   
/* 151 */                   if (score != NodeTest.SCORE_NONE)
/*     */                   {
/*     */ 
/*     */ 
/*     */                     
/* 156 */                     if (score != NodeTest.SCORE_NONE) {
/* 157 */                       return score;
/*     */                     }
/*     */                   }
/*     */ 
/*     */                   
/* 162 */                   xctxt.popCurrentNode(); } finally { xctxt.popCurrentNode(); }
/*     */               
/*     */               } 
/* 165 */               xaxis = 9;
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 172 */           xctxt.popCurrentNode(); } finally { xctxt.popCurrentNode(); }
/*     */       
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 178 */     return score;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/patterns/ContextMatchStepPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */