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
/*     */ public final class ConstantValue
/*     */   extends Attribute
/*     */ {
/*     */   private int constantvalue_index;
/*     */   
/*     */   public ConstantValue(ConstantValue c) {
/*  80 */     this(c.getNameIndex(), c.getLength(), c.getConstantValueIndex(), c
/*  81 */         .getConstantPool());
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
/*     */   ConstantValue(int name_index, int length, DataInputStream file, ConstantPool constant_pool) throws IOException {
/*  95 */     this(name_index, length, file.readUnsignedShort(), constant_pool);
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
/*     */   public ConstantValue(int name_index, int length, int constantvalue_index, ConstantPool constant_pool) {
/* 108 */     super((byte)1, name_index, length, constant_pool);
/* 109 */     this.constantvalue_index = constantvalue_index;
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
/* 120 */     v.visitConstantValue(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void dump(DataOutputStream file) throws IOException {
/* 130 */     super.dump(file);
/* 131 */     file.writeShort(this.constantvalue_index);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getConstantValueIndex() {
/* 136 */     return this.constantvalue_index;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setConstantValueIndex(int constantvalue_index) {
/* 142 */     this.constantvalue_index = constantvalue_index;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String toString() {
/*     */     String buf;
/*     */     int i;
/* 149 */     Constant c = this.constant_pool.getConstant(this.constantvalue_index);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     switch (c.getTag()) { case 5:
/* 156 */         buf = "" + ((ConstantLong)c).getBytes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 170 */         return buf;case 4: buf = "" + ((ConstantFloat)c).getBytes(); return buf;case 6: buf = "" + ((ConstantDouble)c).getBytes(); return buf;case 3: buf = "" + ((ConstantInteger)c).getBytes(); return buf;case 8: i = ((ConstantString)c).getStringIndex(); c = this.constant_pool.getConstant(i, (byte)1); buf = "\"" + Utility.convertString(((ConstantUtf8)c).getBytes()) + "\""; return buf; }
/*     */     
/*     */     throw new IllegalStateException("Type of ConstValue invalid: " + c);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute copy(ConstantPool constant_pool) {
/* 177 */     ConstantValue c = (ConstantValue)clone();
/* 178 */     c.constant_pool = constant_pool;
/* 179 */     return c;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/ConstantValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */