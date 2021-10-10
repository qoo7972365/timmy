/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.ExceptionConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ReturnInstruction
/*     */   extends Instruction
/*     */   implements ExceptionThrower, TypedInstruction, StackConsumer
/*     */ {
/*     */   ReturnInstruction() {}
/*     */   
/*     */   protected ReturnInstruction(short opcode) {
/*  80 */     super(opcode, (short)1);
/*     */   }
/*     */   
/*     */   public Type getType() {
/*  84 */     switch (this.opcode) { case 172:
/*  85 */         return Type.INT;
/*  86 */       case 173: return Type.LONG;
/*  87 */       case 174: return Type.FLOAT;
/*  88 */       case 175: return Type.DOUBLE;
/*  89 */       case 176: return Type.OBJECT;
/*  90 */       case 177: return Type.VOID; }
/*     */ 
/*     */     
/*  93 */     throw new ClassGenException("Unknown type " + this.opcode);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class[] getExceptions() {
/*  98 */     return new Class[] { ExceptionConstants.ILLEGAL_MONITOR_STATE };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType(ConstantPoolGen cp) {
/* 104 */     return getType();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/ReturnInstruction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */