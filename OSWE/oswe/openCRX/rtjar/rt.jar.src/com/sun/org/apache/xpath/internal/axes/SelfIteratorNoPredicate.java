/*     */ package com.sun.org.apache.xpath.internal.axes;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
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
/*     */ public class SelfIteratorNoPredicate
/*     */   extends LocPathIterator
/*     */ {
/*     */   static final long serialVersionUID = -4226887905279814201L;
/*     */   
/*     */   SelfIteratorNoPredicate(Compiler compiler, int opPos, int analysis) throws TransformerException {
/*  52 */     super(compiler, opPos, analysis, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelfIteratorNoPredicate() throws TransformerException {
/*  63 */     super((PrefixResolver)null);
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
/*     */   public int nextNode() {
/*  77 */     if (this.m_foundLast) {
/*  78 */       return -1;
/*     */     }
/*     */     
/*  81 */     DTM dtm = this.m_cdtm;
/*     */     
/*  83 */     int next = (-1 == this.m_lastFetched) ? this.m_context : -1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     if (-1 != next) {
/*     */       
/*  90 */       this.m_pos++;
/*     */       
/*  92 */       return next;
/*     */     } 
/*     */ 
/*     */     
/*  96 */     this.m_foundLast = true;
/*     */     
/*  98 */     return -1;
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
/*     */   public int asNode(XPathContext xctxt) throws TransformerException {
/* 113 */     return xctxt.getCurrentNode();
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
/*     */   public int getLastPos(XPathContext xctxt) {
/* 126 */     return 1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/axes/SelfIteratorNoPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */