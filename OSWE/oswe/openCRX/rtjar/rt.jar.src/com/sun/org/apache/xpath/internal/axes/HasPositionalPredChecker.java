/*     */ package com.sun.org.apache.xpath.internal.axes;
/*     */ 
/*     */ import com.sun.org.apache.xpath.internal.Expression;
/*     */ import com.sun.org.apache.xpath.internal.ExpressionOwner;
/*     */ import com.sun.org.apache.xpath.internal.XPathVisitor;
/*     */ import com.sun.org.apache.xpath.internal.functions.Function;
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
/*     */ public class HasPositionalPredChecker
/*     */   extends XPathVisitor
/*     */ {
/*     */   private boolean m_hasPositionalPred = false;
/*  43 */   private int m_predDepth = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean check(LocPathIterator path) {
/*  54 */     HasPositionalPredChecker hppc = new HasPositionalPredChecker();
/*  55 */     path.callVisitors(null, hppc);
/*  56 */     return hppc.m_hasPositionalPred;
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
/*     */   public boolean visitFunction(ExpressionOwner owner, Function func) {
/*  68 */     if (func instanceof com.sun.org.apache.xpath.internal.functions.FuncPosition || func instanceof com.sun.org.apache.xpath.internal.functions.FuncLast)
/*     */     {
/*  70 */       this.m_hasPositionalPred = true; } 
/*  71 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitPredicate(ExpressionOwner owner, Expression pred) {
/*  99 */     this.m_predDepth++;
/*     */     
/* 101 */     if (this.m_predDepth == 1)
/*     */     {
/* 103 */       if (pred instanceof com.sun.org.apache.xpath.internal.operations.Variable || pred instanceof com.sun.org.apache.xpath.internal.objects.XNumber || pred instanceof com.sun.org.apache.xpath.internal.operations.Div || pred instanceof com.sun.org.apache.xpath.internal.operations.Plus || pred instanceof com.sun.org.apache.xpath.internal.operations.Minus || pred instanceof com.sun.org.apache.xpath.internal.operations.Mod || pred instanceof com.sun.org.apache.xpath.internal.operations.Quo || pred instanceof com.sun.org.apache.xpath.internal.operations.Mult || pred instanceof com.sun.org.apache.xpath.internal.operations.Number || pred instanceof Function) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 113 */         this.m_hasPositionalPred = true;
/*     */       } else {
/* 115 */         pred.callVisitors(owner, this);
/*     */       } 
/*     */     }
/* 118 */     this.m_predDepth--;
/*     */ 
/*     */     
/* 121 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/axes/HasPositionalPredChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */