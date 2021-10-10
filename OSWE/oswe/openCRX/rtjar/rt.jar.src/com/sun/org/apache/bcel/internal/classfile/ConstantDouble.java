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
/*     */ public final class ConstantDouble
/*     */   extends Constant
/*     */   implements ConstantObject
/*     */ {
/*     */   private double bytes;
/*     */   
/*     */   public ConstantDouble(double bytes) {
/*  79 */     super((byte)6);
/*  80 */     this.bytes = bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConstantDouble(ConstantDouble c) {
/*  87 */     this(c.getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ConstantDouble(DataInputStream file) throws IOException {
/*  98 */     this(file.readDouble());
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
/* 109 */     v.visitConstantDouble(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void dump(DataOutputStream file) throws IOException {
/* 119 */     file.writeByte(this.tag);
/* 120 */     file.writeDouble(this.bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public final double getBytes() {
/* 125 */     return this.bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setBytes(double bytes) {
/* 130 */     this.bytes = bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 137 */     return super.toString() + "(bytes = " + this.bytes + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getConstantValue(ConstantPool cp) {
/* 143 */     return new Double(this.bytes);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/ConstantDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */