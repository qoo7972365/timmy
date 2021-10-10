/*     */ package com.sun.org.apache.xpath.internal.axes;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xpath.internal.Expression;
/*     */ import com.sun.org.apache.xpath.internal.ExpressionOwner;
/*     */ import com.sun.org.apache.xpath.internal.XPathVisitor;
/*     */ import com.sun.org.apache.xpath.internal.objects.XNodeSet;
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
/*     */ public class FilterExprIterator
/*     */   extends BasicTestIterator
/*     */ {
/*     */   static final long serialVersionUID = 2552176105165737614L;
/*     */   private Expression m_expr;
/*     */   private transient XNodeSet m_exprObj;
/*     */   private boolean m_mustHardReset = false;
/*     */   private boolean m_canDetachNodeset = true;
/*     */   
/*     */   public FilterExprIterator() {
/*  50 */     super((PrefixResolver)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilterExprIterator(Expression expr) {
/*  59 */     super((PrefixResolver)null);
/*  60 */     this.m_expr = expr;
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
/*     */   public void setRoot(int context, Object environment) {
/*  72 */     super.setRoot(context, environment);
/*     */     
/*  74 */     this.m_exprObj = FilterExprIteratorSimple.executeFilterExpr(context, this.m_execContext, 
/*  75 */         getPrefixResolver(), 
/*  76 */         getIsTopLevel(), this.m_stackFrame, this.m_expr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getNextNode() {
/*  86 */     if (null != this.m_exprObj) {
/*     */       
/*  88 */       this.m_lastFetched = this.m_exprObj.nextNode();
/*     */     } else {
/*     */       
/*  91 */       this.m_lastFetched = -1;
/*     */     } 
/*  93 */     return this.m_lastFetched;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detach() {
/* 103 */     super.detach();
/* 104 */     this.m_exprObj.detach();
/* 105 */     this.m_exprObj = null;
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
/*     */   public void fixupVariables(Vector vars, int globalsSize) {
/* 120 */     super.fixupVariables(vars, globalsSize);
/* 121 */     this.m_expr.fixupVariables(vars, globalsSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression getInnerExpression() {
/* 129 */     return this.m_expr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInnerExpression(Expression expr) {
/* 137 */     expr.exprSetParent(this);
/* 138 */     this.m_expr = expr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAnalysisBits() {
/* 147 */     if (null != this.m_expr && this.m_expr instanceof PathComponent)
/*     */     {
/* 149 */       return ((PathComponent)this.m_expr).getAnalysisBits();
/*     */     }
/* 151 */     return 67108864;
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
/*     */   public boolean isDocOrdered() {
/* 163 */     return this.m_exprObj.isDocOrdered();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class filterExprOwner
/*     */     implements ExpressionOwner
/*     */   {
/*     */     public Expression getExpression() {
/* 173 */       return FilterExprIterator.this.m_expr;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setExpression(Expression exp) {
/* 181 */       exp.exprSetParent(FilterExprIterator.this);
/* 182 */       FilterExprIterator.this.m_expr = exp;
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
/*     */   public void callPredicateVisitors(XPathVisitor visitor) {
/* 196 */     this.m_expr.callVisitors(new filterExprOwner(), visitor);
/*     */     
/* 198 */     super.callPredicateVisitors(visitor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deepEquals(Expression expr) {
/* 206 */     if (!super.deepEquals(expr)) {
/* 207 */       return false;
/*     */     }
/* 209 */     FilterExprIterator fet = (FilterExprIterator)expr;
/* 210 */     if (!this.m_expr.deepEquals(fet.m_expr)) {
/* 211 */       return false;
/*     */     }
/* 213 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/axes/FilterExprIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */