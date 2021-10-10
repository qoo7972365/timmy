/*     */ package com.sun.org.apache.xpath.internal.axes;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMIterator;
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xpath.internal.VariableStack;
/*     */ import com.sun.org.apache.xpath.internal.compiler.Compiler;
/*     */ import com.sun.org.apache.xpath.internal.compiler.OpMap;
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
/*     */ public abstract class BasicTestIterator
/*     */   extends LocPathIterator
/*     */ {
/*     */   static final long serialVersionUID = 3505378079378096623L;
/*     */   
/*     */   protected BasicTestIterator() {}
/*     */   
/*     */   protected BasicTestIterator(PrefixResolver nscontext) {
/*  60 */     super(nscontext);
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
/*     */   protected BasicTestIterator(Compiler compiler, int opPos, int analysis) throws TransformerException {
/*  78 */     super(compiler, opPos, analysis, false);
/*     */     
/*  80 */     int firstStepPos = OpMap.getFirstChildPos(opPos);
/*  81 */     int whatToShow = compiler.getWhatToShow(firstStepPos);
/*     */     
/*  83 */     if (0 == (whatToShow & 0x1043) || whatToShow == -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       initNodeTest(whatToShow);
/*     */     } else {
/*     */       
/*  92 */       initNodeTest(whatToShow, compiler.getStepNS(firstStepPos), compiler
/*  93 */           .getStepLocalName(firstStepPos));
/*     */     } 
/*  95 */     initPredicateInfo(compiler, firstStepPos);
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
/*     */   protected BasicTestIterator(Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers) throws TransformerException {
/* 117 */     super(compiler, opPos, analysis, shouldLoadWalkers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getNextNode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextNode() {
/*     */     int next;
/*     */     VariableStack vars;
/*     */     int savedStart;
/* 137 */     if (this.m_foundLast) {
/*     */       
/* 139 */       this.m_lastFetched = -1;
/* 140 */       return -1;
/*     */     } 
/*     */     
/* 143 */     if (-1 == this.m_lastFetched)
/*     */     {
/* 145 */       resetProximityPositions();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     if (-1 != this.m_stackFrame) {
/*     */       
/* 154 */       vars = this.m_execContext.getVarStack();
/*     */ 
/*     */       
/* 157 */       savedStart = vars.getStackFrame();
/*     */       
/* 159 */       vars.setStackFrame(this.m_stackFrame);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 164 */       vars = null;
/* 165 */       savedStart = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 172 */       next = getNextNode();
/*     */       
/* 174 */       if (-1 != next)
/*     */       
/* 176 */       { if (1 == acceptNode(next)) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 184 */         if (next == -1)
/*     */           break;  continue; }  break;
/* 186 */     }  if (-1 != next)
/*     */     
/* 188 */     { this.m_pos++;
/* 189 */       int i = next;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 200 */       if (-1 != this.m_stackFrame)
/*     */       {
/*     */         
/* 203 */         vars.setStackFrame(savedStart); }  return i; }  this.m_foundLast = true; byte b = -1; if (-1 != this.m_stackFrame) vars.setStackFrame(savedStart);
/*     */     
/*     */     return b;
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
/*     */   public DTMIterator cloneWithReset() throws CloneNotSupportedException {
/* 219 */     ChildTestIterator clone = (ChildTestIterator)super.cloneWithReset();
/*     */     
/* 221 */     clone.resetProximityPositions();
/*     */     
/* 223 */     return clone;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/axes/BasicTestIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */