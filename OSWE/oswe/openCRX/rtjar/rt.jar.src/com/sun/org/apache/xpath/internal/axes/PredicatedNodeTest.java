/*     */ package com.sun.org.apache.xpath.internal.axes;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*     */ import com.sun.org.apache.xpath.internal.Expression;
/*     */ import com.sun.org.apache.xpath.internal.ExpressionOwner;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.XPathVisitor;
/*     */ import com.sun.org.apache.xpath.internal.compiler.Compiler;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import com.sun.org.apache.xpath.internal.patterns.NodeTest;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PredicatedNodeTest
/*     */   extends NodeTest
/*     */   implements SubContextList
/*     */ {
/*     */   static final long serialVersionUID = -6193530757296377351L;
/*     */   protected int m_predCount;
/*     */   protected transient boolean m_foundLast;
/*     */   protected LocPathIterator m_lpi;
/*     */   transient int m_predicateIndex;
/*     */   private Expression[] m_predicates;
/*     */   protected transient int[] m_proximityPositions;
/*     */   static final boolean DEBUG_PREDICATECOUNTING = false;
/*     */   
/*     */   PredicatedNodeTest(LocPathIterator locPathIterator) {
/* 118 */     this.m_predCount = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 602 */     this.m_foundLast = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 611 */     this.m_predicateIndex = -1; this.m_lpi = locPathIterator; } private void readObject(ObjectInputStream stream) throws IOException, TransformerException { try { stream.defaultReadObject(); this.m_predicateIndex = -1; this.m_predCount = -1; resetProximityPositions(); } catch (ClassNotFoundException cnfe) { throw new TransformerException(cnfe); }  } public Object clone() throws CloneNotSupportedException { PredicatedNodeTest clone = (PredicatedNodeTest)super.clone(); if (null != this.m_proximityPositions && this.m_proximityPositions == clone.m_proximityPositions) { clone.m_proximityPositions = new int[this.m_proximityPositions.length]; System.arraycopy(this.m_proximityPositions, 0, clone.m_proximityPositions, 0, this.m_proximityPositions.length); }  if (clone.m_lpi == this) clone.m_lpi = (LocPathIterator)clone;  return clone; } public int getPredicateCount() { if (-1 == this.m_predCount) return (null == this.m_predicates) ? 0 : this.m_predicates.length;  return this.m_predCount; } public void setPredicateCount(int count) { if (count > 0) { Expression[] newPredicates = new Expression[count]; for (int i = 0; i < count; i++) newPredicates[i] = this.m_predicates[i];  this.m_predicates = newPredicates; } else { this.m_predicates = null; }  } protected void initPredicateInfo(Compiler compiler, int opPos) throws TransformerException { int pos = compiler.getFirstPredicateOpPos(opPos); if (pos > 0) { this.m_predicates = compiler.getCompiledPredicates(pos); if (null != this.m_predicates) for (int i = 0; i < this.m_predicates.length; i++) this.m_predicates[i].exprSetParent(this);   }  } public Expression getPredicate(int index) { return this.m_predicates[index]; } public int getProximityPosition() { return getProximityPosition(this.m_predicateIndex); } public int getProximityPosition(XPathContext xctxt) { return getProximityPosition(); } protected int getProximityPosition(int predicateIndex) { return (predicateIndex >= 0) ? this.m_proximityPositions[predicateIndex] : 0; } public void resetProximityPositions() { int nPredicates = getPredicateCount(); if (nPredicates > 0) { if (null == this.m_proximityPositions) this.m_proximityPositions = new int[nPredicates];  for (int i = 0; i < nPredicates; i++) { try { initProximityPosition(i); } catch (Exception e) { throw new WrappedRuntimeException(e); }  }  }  } public void initProximityPosition(int i) throws TransformerException { this.m_proximityPositions[i] = 0; } protected void countProximityPosition(int i) { int[] pp = this.m_proximityPositions; if (null != pp && i < pp.length) pp[i] = pp[i] + 1;  } PredicatedNodeTest() { this.m_predCount = -1; this.m_foundLast = false; this.m_predicateIndex = -1; }
/*     */   public boolean isReverseAxes() { return false; }
/*     */   public int getPredicateIndex() { return this.m_predicateIndex; }
/*     */   boolean executePredicates(int context, XPathContext xctxt) throws TransformerException { int nPredicates = getPredicateCount(); if (nPredicates == 0)
/*     */       return true;  PrefixResolver savedResolver = xctxt.getNamespaceContext(); try { this.m_predicateIndex = 0; xctxt.pushSubContextList(this); xctxt.pushNamespaceContext(this.m_lpi.getPrefixResolver()); xctxt.pushCurrentNode(context); for (int i = 0; i < nPredicates; i++) { XObject pred = this.m_predicates[i].execute(xctxt); if (2 == pred.getType()) { int proxPos = getProximityPosition(this.m_predicateIndex); int predIndex = (int)pred.num(); if (proxPos != predIndex)
/*     */             return false;  if (this.m_predicates[i].isStableNumber() && i == nPredicates - 1)
/*     */             this.m_foundLast = true;  } else if (!pred.bool()) { return false; }  countProximityPosition(++this.m_predicateIndex); }  } finally { xctxt.popCurrentNode(); xctxt.popNamespaceContext(); xctxt.popSubContextList(); this.m_predicateIndex = -1; }  return true; }
/*     */   public void fixupVariables(Vector vars, int globalsSize) { super.fixupVariables(vars, globalsSize); int nPredicates = getPredicateCount(); for (int i = 0; i < nPredicates; i++)
/*     */       this.m_predicates[i].fixupVariables(vars, globalsSize);  }
/*     */   protected String nodeToString(int n) { if (-1 != n) { DTM dtm = this.m_lpi.getXPathContext().getDTM(n); return dtm.getNodeName(n) + "{" + (n + 1) + "}"; }  return "null"; }
/*     */   public short acceptNode(int n) { XPathContext xctxt = this.m_lpi.getXPathContext(); try { xctxt.pushCurrentNode(n); XObject score = execute(xctxt, n); if (score != NodeTest.SCORE_NONE) { if (getPredicateCount() > 0) { countProximityPosition(0); if (!executePredicates(n, xctxt))
/*     */             return 3;  }  return 1; }  } catch (TransformerException se) { throw new RuntimeException(se.getMessage()); } finally { xctxt.popCurrentNode(); }  return 3; }
/*     */   public LocPathIterator getLocPathIterator() { return this.m_lpi; }
/*     */   public void setLocPathIterator(LocPathIterator li) { this.m_lpi = li; if (this != li)
/*     */       li.exprSetParent(this);  }
/*     */   public boolean canTraverseOutsideSubtree() { int n = getPredicateCount(); for (int i = 0; i < n; i++) { if (getPredicate(i).canTraverseOutsideSubtree())
/*     */         return true;  }  return false; } public void callPredicateVisitors(XPathVisitor visitor) { if (null != this.m_predicates) { int n = this.m_predicates.length; for (int i = 0; i < n; i++) { ExpressionOwner predOwner = new PredOwner(i); if (visitor.visitPredicate(predOwner, this.m_predicates[i]))
/*     */           this.m_predicates[i].callVisitors(predOwner, visitor);  }  }  } public boolean deepEquals(Expression expr) { if (!super.deepEquals(expr))
/*     */       return false;  PredicatedNodeTest pnt = (PredicatedNodeTest)expr; if (null != this.m_predicates) { int n = this.m_predicates.length; if (null == pnt.m_predicates || pnt.m_predicates.length != n)
/*     */         return false;  for (int i = 0; i < n; i++) { if (!this.m_predicates[i].deepEquals(pnt.m_predicates[i]))
/*     */           return false;  }  } else if (null != pnt.m_predicates) { return false; }
/*     */      return true; } public abstract int getLastPos(XPathContext paramXPathContext); class PredOwner implements ExpressionOwner
/*     */   {
/* 634 */     PredOwner(int index) { this.m_index = index; }
/*     */ 
/*     */ 
/*     */     
/*     */     int m_index;
/*     */ 
/*     */     
/*     */     public Expression getExpression() {
/* 642 */       return PredicatedNodeTest.this.m_predicates[this.m_index];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setExpression(Expression exp) {
/* 651 */       exp.exprSetParent(PredicatedNodeTest.this);
/* 652 */       PredicatedNodeTest.this.m_predicates[this.m_index] = exp;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/axes/PredicatedNodeTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */