/*     */ package com.sun.xml.internal.ws.org.objectweb.asm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodAdapter
/*     */   implements MethodVisitor
/*     */ {
/*     */   protected MethodVisitor mv;
/*     */   
/*     */   public MethodAdapter(MethodVisitor mv) {
/*  83 */     this.mv = mv;
/*     */   }
/*     */   
/*     */   public AnnotationVisitor visitAnnotationDefault() {
/*  87 */     return this.mv.visitAnnotationDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  94 */     return this.mv.visitAnnotation(desc, visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
/* 102 */     return this.mv.visitParameterAnnotation(parameter, desc, visible);
/*     */   }
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 106 */     this.mv.visitAttribute(attr);
/*     */   }
/*     */   
/*     */   public void visitCode() {
/* 110 */     this.mv.visitCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/* 120 */     this.mv.visitFrame(type, nLocal, local, nStack, stack);
/*     */   }
/*     */   
/*     */   public void visitInsn(int opcode) {
/* 124 */     this.mv.visitInsn(opcode);
/*     */   }
/*     */   
/*     */   public void visitIntInsn(int opcode, int operand) {
/* 128 */     this.mv.visitIntInsn(opcode, operand);
/*     */   }
/*     */   
/*     */   public void visitVarInsn(int opcode, int var) {
/* 132 */     this.mv.visitVarInsn(opcode, var);
/*     */   }
/*     */   
/*     */   public void visitTypeInsn(int opcode, String type) {
/* 136 */     this.mv.visitTypeInsn(opcode, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/* 145 */     this.mv.visitFieldInsn(opcode, owner, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/* 154 */     this.mv.visitMethodInsn(opcode, owner, name, desc);
/*     */   }
/*     */   
/*     */   public void visitJumpInsn(int opcode, Label label) {
/* 158 */     this.mv.visitJumpInsn(opcode, label);
/*     */   }
/*     */   
/*     */   public void visitLabel(Label label) {
/* 162 */     this.mv.visitLabel(label);
/*     */   }
/*     */   
/*     */   public void visitLdcInsn(Object cst) {
/* 166 */     this.mv.visitLdcInsn(cst);
/*     */   }
/*     */   
/*     */   public void visitIincInsn(int var, int increment) {
/* 170 */     this.mv.visitIincInsn(var, increment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
/* 179 */     this.mv.visitTableSwitchInsn(min, max, dflt, labels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/* 187 */     this.mv.visitLookupSwitchInsn(dflt, keys, labels);
/*     */   }
/*     */   
/*     */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 191 */     this.mv.visitMultiANewArrayInsn(desc, dims);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/* 200 */     this.mv.visitTryCatchBlock(start, end, handler, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 211 */     this.mv.visitLocalVariable(name, desc, signature, start, end, index);
/*     */   }
/*     */   
/*     */   public void visitLineNumber(int line, Label start) {
/* 215 */     this.mv.visitLineNumber(line, start);
/*     */   }
/*     */   
/*     */   public void visitMaxs(int maxStack, int maxLocals) {
/* 219 */     this.mv.visitMaxs(maxStack, maxLocals);
/*     */   }
/*     */   
/*     */   public void visitEnd() {
/* 223 */     this.mv.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/org/objectweb/asm/MethodAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */