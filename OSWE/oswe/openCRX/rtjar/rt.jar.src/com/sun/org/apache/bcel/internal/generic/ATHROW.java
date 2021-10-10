/*    */ package com.sun.org.apache.bcel.internal.generic;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.ExceptionConstants;
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
/*    */ public class ATHROW
/*    */   extends Instruction
/*    */   implements UnconditionalBranch, ExceptionThrower
/*    */ {
/*    */   public ATHROW() {
/* 72 */     super((short)191, (short)1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Class[] getExceptions() {
/* 78 */     return new Class[] { ExceptionConstants.THROWABLE };
/*    */   }
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
/*    */   public void accept(Visitor v) {
/* 91 */     v.visitUnconditionalBranch(this);
/* 92 */     v.visitExceptionThrower(this);
/* 93 */     v.visitATHROW(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/ATHROW.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */