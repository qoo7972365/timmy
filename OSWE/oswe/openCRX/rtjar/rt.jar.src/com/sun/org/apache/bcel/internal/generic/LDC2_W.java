/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.classfile.Constant;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantDouble;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LDC2_W
/*     */   extends CPInstruction
/*     */   implements PushInstruction, TypedInstruction
/*     */ {
/*     */   LDC2_W() {}
/*     */   
/*     */   public LDC2_W(int index) {
/*  77 */     super((short)20, index);
/*     */   }
/*     */   
/*     */   public Type getType(ConstantPoolGen cpg) {
/*  81 */     switch (cpg.getConstantPool().getConstant(this.index).getTag()) { case 5:
/*  82 */         return Type.LONG;
/*  83 */       case 6: return Type.DOUBLE; }
/*     */     
/*  85 */     throw new RuntimeException("Unknown constant type " + this.opcode);
/*     */   }
/*     */ 
/*     */   
/*     */   public Number getValue(ConstantPoolGen cpg) {
/*  90 */     Constant c = cpg.getConstantPool().getConstant(this.index);
/*     */     
/*  92 */     switch (c.getTag()) {
/*     */       case 5:
/*  94 */         return new Long(((ConstantLong)c).getBytes());
/*     */       
/*     */       case 6:
/*  97 */         return new Double(((ConstantDouble)c).getBytes());
/*     */     } 
/*     */     
/* 100 */     throw new RuntimeException("Unknown or invalid constant type at " + this.index);
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
/*     */   public void accept(Visitor v) {
/* 113 */     v.visitStackProducer(this);
/* 114 */     v.visitPushInstruction(this);
/* 115 */     v.visitTypedInstruction(this);
/* 116 */     v.visitCPInstruction(this);
/* 117 */     v.visitLDC2_W(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/LDC2_W.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */