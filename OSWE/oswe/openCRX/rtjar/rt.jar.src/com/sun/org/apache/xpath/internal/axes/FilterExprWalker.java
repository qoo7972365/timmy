/*     */ package com.sun.org.apache.xpath.internal.axes;
/*     */ 
/*     */ import com.sun.org.apache.xpath.internal.Expression;
/*     */ import com.sun.org.apache.xpath.internal.ExpressionOwner;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.XPathVisitor;
/*     */ import com.sun.org.apache.xpath.internal.compiler.Compiler;
/*     */ import com.sun.org.apache.xpath.internal.objects.XNodeSet;
/*     */ import java.util.Vector;
/*     */ import javax.xml.transform.TransformerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilterExprWalker
/*     */   extends AxesWalker
/*     */ {
/*     */   static final long serialVersionUID = 5457182471424488375L;
/*     */   private Expression m_expr;
/*     */   private transient XNodeSet m_exprObj;
/*     */   private boolean m_mustHardReset;
/*     */   private boolean m_canDetachNodeset;
/*     */   
/*     */   public void init(Compiler compiler, int opPos, int stepType) throws TransformerException {
/*     */     super.init(compiler, opPos, stepType);
/*     */     switch (stepType) {
/*     */       case 24:
/*     */       case 25:
/*     */         this.m_mustHardReset = true;
/*     */       case 22:
/*     */       case 23:
/*     */         this.m_expr = compiler.compileExpression(opPos);
/*     */         this.m_expr.exprSetParent(this);
/*     */         if (this.m_expr instanceof com.sun.org.apache.xpath.internal.operations.Variable)
/*     */           this.m_canDetachNodeset = false; 
/*     */         return;
/*     */     } 
/*     */     this.m_expr = compiler.compileExpression(opPos + 2);
/*     */     this.m_expr.exprSetParent(this);
/*     */   }
/*     */   
/*     */   public void detach() {
/*     */     super.detach();
/*     */     if (this.m_canDetachNodeset)
/*     */       this.m_exprObj.detach(); 
/*     */     this.m_exprObj = null;
/*     */   }
/*     */   
/*     */   public FilterExprWalker(WalkingIterator locPathIterator) {
/*  52 */     super(locPathIterator, 20);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     this.m_mustHardReset = false;
/* 228 */     this.m_canDetachNodeset = true;
/*     */   }
/*     */   public void setRoot(int root) { super.setRoot(root); this.m_exprObj = FilterExprIteratorSimple.executeFilterExpr(root, this.m_lpi.getXPathContext(), this.m_lpi.getPrefixResolver(), this.m_lpi.getIsTopLevel(), this.m_lpi.m_stackFrame, this.m_expr); }
/*     */   public Object clone() throws CloneNotSupportedException { FilterExprWalker clone = (FilterExprWalker)super.clone(); if (null != this.m_exprObj)
/*     */       clone.m_exprObj = (XNodeSet)this.m_exprObj.clone(); 
/*     */     return clone; } public short acceptNode(int n) { try {
/*     */       if (getPredicateCount() > 0) {
/*     */         countProximityPosition(0);
/*     */         if (!executePredicates(n, this.m_lpi.getXPathContext()))
/*     */           return 3; 
/*     */       } 
/*     */       return 1;
/*     */     } catch (TransformerException se) {
/*     */       throw new RuntimeException(se.getMessage());
/* 242 */     }  } public void fixupVariables(Vector vars, int globalsSize) { super.fixupVariables(vars, globalsSize);
/* 243 */     this.m_expr.fixupVariables(vars, globalsSize); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression getInnerExpression() {
/* 251 */     return this.m_expr; } public int getNextNode() { if (null != this.m_exprObj) {
/*     */       int next = this.m_exprObj.nextNode();
/*     */       return next;
/*     */     } 
/*     */     return -1; } public int getLastPos(XPathContext xctxt) {
/*     */     return this.m_exprObj.getLength();
/*     */   }
/*     */   public void setInnerExpression(Expression expr) {
/* 259 */     expr.exprSetParent(this);
/* 260 */     this.m_expr = expr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAnalysisBits() {
/* 270 */     if (null != this.m_expr && this.m_expr instanceof PathComponent)
/*     */     {
/* 272 */       return ((PathComponent)this.m_expr).getAnalysisBits();
/*     */     }
/* 274 */     return 67108864;
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
/* 286 */     return this.m_exprObj.isDocOrdered();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAxis() {
/* 297 */     return this.m_exprObj.getAxis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class filterExprOwner
/*     */     implements ExpressionOwner
/*     */   {
/*     */     public Expression getExpression() {
/* 307 */       return FilterExprWalker.this.m_expr;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setExpression(Expression exp) {
/* 315 */       exp.exprSetParent(FilterExprWalker.this);
/* 316 */       FilterExprWalker.this.m_expr = exp;
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
/*     */   public void callPredicateVisitors(XPathVisitor visitor) {
/* 329 */     this.m_expr.callVisitors(new filterExprOwner(), visitor);
/*     */     
/* 331 */     super.callPredicateVisitors(visitor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deepEquals(Expression expr) {
/* 340 */     if (!super.deepEquals(expr)) {
/* 341 */       return false;
/*     */     }
/* 343 */     FilterExprWalker walker = (FilterExprWalker)expr;
/* 344 */     if (!this.m_expr.deepEquals(walker.m_expr)) {
/* 345 */       return false;
/*     */     }
/* 347 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/axes/FilterExprWalker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */