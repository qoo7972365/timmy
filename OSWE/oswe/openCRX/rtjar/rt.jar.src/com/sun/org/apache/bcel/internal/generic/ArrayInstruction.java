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
/*     */ public abstract class ArrayInstruction
/*     */   extends Instruction
/*     */   implements ExceptionThrower, TypedInstruction
/*     */ {
/*     */   ArrayInstruction() {}
/*     */   
/*     */   protected ArrayInstruction(short opcode) {
/*  78 */     super(opcode, (short)1);
/*     */   }
/*     */   
/*     */   public Class[] getExceptions() {
/*  82 */     return ExceptionConstants.EXCS_ARRAY_EXCEPTION;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType(ConstantPoolGen cp) {
/*  88 */     switch (this.opcode) { case 46:
/*     */       case 79:
/*  90 */         return Type.INT;
/*     */       case 52: case 85:
/*  92 */         return Type.CHAR;
/*     */       case 51: case 84:
/*  94 */         return Type.BYTE;
/*     */       case 53: case 86:
/*  96 */         return Type.SHORT;
/*     */       case 47: case 80:
/*  98 */         return Type.LONG;
/*     */       case 49: case 82:
/* 100 */         return Type.DOUBLE;
/*     */       case 48: case 81:
/* 102 */         return Type.FLOAT;
/*     */       case 50: case 83:
/* 104 */         return Type.OBJECT; }
/*     */     
/* 106 */     throw new ClassGenException("Oops: unknown case in switch" + this.opcode);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/ArrayInstruction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */