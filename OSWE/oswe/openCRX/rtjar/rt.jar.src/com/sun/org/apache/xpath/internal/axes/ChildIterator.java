/*     */ package com.sun.org.apache.xpath.internal.axes;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.compiler.Compiler;
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
/*     */ public class ChildIterator
/*     */   extends LocPathIterator
/*     */ {
/*     */   static final long serialVersionUID = -6935428015142993583L;
/*     */   
/*     */   ChildIterator(Compiler compiler, int opPos, int analysis) throws TransformerException {
/*  54 */     super(compiler, opPos, analysis, false);
/*     */ 
/*     */     
/*  57 */     initNodeTest(-1);
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
/*     */   public int asNode(XPathContext xctxt) throws TransformerException {
/*  71 */     int current = xctxt.getCurrentNode();
/*     */     
/*  73 */     DTM dtm = xctxt.getDTM(current);
/*     */     
/*  75 */     return dtm.getFirstChild(current);
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
/*     */   public int nextNode() {
/*  88 */     if (this.m_foundLast) {
/*  89 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     int next = (-1 == this.m_lastFetched) ? this.m_cdtm.getFirstChild(this.m_context) : this.m_cdtm.getNextSibling(this.m_lastFetched);
/*     */ 
/*     */     
/*  98 */     if (-1 != next) {
/*     */       
/* 100 */       this.m_pos++;
/* 101 */       return next;
/*     */     } 
/*     */ 
/*     */     
/* 105 */     this.m_foundLast = true;
/*     */     
/* 107 */     return -1;
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
/*     */   public int getAxis() {
/* 119 */     return 3;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/axes/ChildIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */