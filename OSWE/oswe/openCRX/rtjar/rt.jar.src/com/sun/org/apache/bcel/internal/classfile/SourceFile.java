/*     */ package com.sun.org.apache.bcel.internal.classfile;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SourceFile
/*     */   extends Attribute
/*     */ {
/*     */   private int sourcefile_index;
/*     */   
/*     */   public SourceFile(SourceFile c) {
/*  81 */     this(c.getNameIndex(), c.getLength(), c.getSourceFileIndex(), c
/*  82 */         .getConstantPool());
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
/*     */   SourceFile(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
/*  96 */     this(name_index, length, file.readUnsignedShort(), constant_pool);
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
/*     */   public SourceFile(int name_index, int length, int sourcefile_index, ConstantPool constant_pool) {
/* 115 */     super((byte)0, name_index, length, constant_pool);
/* 116 */     this.sourcefile_index = sourcefile_index;
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
/* 127 */     v.visitSourceFile(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void dump(DataOutputStream file) throws IOException {
/* 138 */     super.dump(file);
/* 139 */     file.writeShort(this.sourcefile_index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getSourceFileIndex() {
/* 145 */     return this.sourcefile_index;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setSourceFileIndex(int sourcefile_index) {
/* 151 */     this.sourcefile_index = sourcefile_index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getSourceFileName() {
/* 158 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.sourcefile_index, (byte)1);
/*     */     
/* 160 */     return c.getBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 167 */     return "SourceFile(" + getSourceFileName() + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute copy(ConstantPool constant_pool) {
/* 174 */     return (SourceFile)clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/SourceFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */