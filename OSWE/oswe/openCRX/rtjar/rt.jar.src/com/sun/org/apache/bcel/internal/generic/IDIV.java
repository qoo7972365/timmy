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
/*    */ public class IDIV
/*    */   extends ArithmeticInstruction
/*    */   implements ExceptionThrower
/*    */ {
/*    */   public IDIV() {
/* 71 */     super((short)108);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Class[] getExceptions() {
/* 77 */     return new Class[] { ExceptionConstants.ARITHMETIC_EXCEPTION };
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
/* 90 */     v.visitExceptionThrower(this);
/* 91 */     v.visitTypedInstruction(this);
/* 92 */     v.visitStackProducer(this);
/* 93 */     v.visitStackConsumer(this);
/* 94 */     v.visitArithmeticInstruction(this);
/* 95 */     v.visitIDIV(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/IDIV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */