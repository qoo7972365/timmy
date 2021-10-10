/*    */ package com.sun.org.apache.xpath.internal.axes;
/*    */ 
/*    */ import com.sun.org.apache.xpath.internal.compiler.Compiler;
/*    */ import javax.xml.transform.TransformerException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeIterator
/*    */   extends ChildTestIterator
/*    */ {
/*    */   static final long serialVersionUID = -8417986700712229686L;
/*    */   
/*    */   AttributeIterator(Compiler compiler, int opPos, int analysis) throws TransformerException {
/* 50 */     super(compiler, opPos, analysis);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getNextNode() {
/* 58 */     this
/*    */       
/* 60 */       .m_lastFetched = (-1 == this.m_lastFetched) ? this.m_cdtm.getFirstAttribute(this.m_context) : this.m_cdtm.getNextAttribute(this.m_lastFetched);
/* 61 */     return this.m_lastFetched;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getAxis() {
/* 72 */     return 2;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/axes/AttributeIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */