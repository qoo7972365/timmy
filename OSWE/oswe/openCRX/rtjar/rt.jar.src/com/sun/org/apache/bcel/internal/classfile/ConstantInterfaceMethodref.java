/*     */ package com.sun.org.apache.bcel.internal.classfile;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConstantInterfaceMethodref
/*     */   extends ConstantCP
/*     */ {
/*     */   public ConstantInterfaceMethodref(ConstantInterfaceMethodref c) {
/*  74 */     super((byte)11, c.getClassIndex(), c.getNameAndTypeIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ConstantInterfaceMethodref(DataInputStream file) throws IOException {
/*  85 */     super((byte)11, file);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConstantInterfaceMethodref(int class_index, int name_and_type_index) {
/*  94 */     super((byte)11, class_index, name_and_type_index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(Visitor v) {
/* 105 */     v.visitConstantInterfaceMethodref(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/ConstantInterfaceMethodref.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */