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
/*     */ public final class ConstantFloat
/*     */   extends Constant
/*     */   implements ConstantObject
/*     */ {
/*     */   private float bytes;
/*     */   
/*     */   public ConstantFloat(float bytes) {
/*  80 */     super((byte)4);
/*  81 */     this.bytes = bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConstantFloat(ConstantFloat c) {
/*  88 */     this(c.getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ConstantFloat(DataInputStream file) throws IOException {
/*  98 */     this(file.readFloat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(Visitor v) {
/* 108 */     v.visitConstantFloat(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void dump(DataOutputStream file) throws IOException {
/* 118 */     file.writeByte(this.tag);
/* 119 */     file.writeFloat(this.bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public final float getBytes() {
/* 124 */     return this.bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setBytes(float bytes) {
/* 129 */     this.bytes = bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 136 */     return super.toString() + "(bytes = " + this.bytes + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getConstantValue(ConstantPool cp) {
/* 142 */     return new Float(this.bytes);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/ConstantFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */