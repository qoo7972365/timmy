/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.Constants;
/*     */ import com.sun.org.apache.bcel.internal.classfile.ConstantPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FieldInstruction
/*     */   extends FieldOrMethod
/*     */   implements TypedInstruction
/*     */ {
/*     */   FieldInstruction() {}
/*     */   
/*     */   protected FieldInstruction(short opcode, int index) {
/*  84 */     super(opcode, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(ConstantPool cp) {
/*  91 */     return Constants.OPCODE_NAMES[this.opcode] + " " + cp
/*  92 */       .constantToString(this.index, (byte)9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getFieldSize(ConstantPoolGen cpg) {
/*  98 */     return getType(cpg).getSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType(ConstantPoolGen cpg) {
/* 104 */     return getFieldType(cpg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getFieldType(ConstantPoolGen cpg) {
/* 110 */     return Type.getType(getSignature(cpg));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFieldName(ConstantPoolGen cpg) {
/* 116 */     return getName(cpg);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/FieldInstruction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */