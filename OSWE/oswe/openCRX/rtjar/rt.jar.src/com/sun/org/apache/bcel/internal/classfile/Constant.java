/*     */ package com.sun.org.apache.bcel.internal.classfile;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.Constants;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Constant
/*     */   implements Cloneable, Node, Serializable
/*     */ {
/*     */   protected byte tag;
/*     */   
/*     */   Constant(byte tag) {
/*  82 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void accept(Visitor paramVisitor);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void dump(DataOutputStream paramDataOutputStream) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte getTag() {
/*  99 */     return this.tag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return Constants.CONSTANT_NAMES[this.tag] + "[" + this.tag + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Constant copy() {
/*     */     try {
/* 113 */       return (Constant)super.clone();
/* 114 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 116 */       return null;
/*     */     } 
/*     */   }
/*     */   public Object clone() throws CloneNotSupportedException {
/* 120 */     return super.clone();
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
/*     */   static final Constant readConstant(DataInputStream file) throws IOException, ClassFormatException {
/* 132 */     byte b = file.readByte();
/*     */     
/* 134 */     switch (b) { case 7:
/* 135 */         return new ConstantClass(file);
/* 136 */       case 9: return new ConstantFieldref(file);
/* 137 */       case 10: return new ConstantMethodref(file);
/* 138 */       case 11: return new ConstantInterfaceMethodref(file);
/*     */       case 8:
/* 140 */         return new ConstantString(file);
/* 141 */       case 3: return new ConstantInteger(file);
/* 142 */       case 4: return new ConstantFloat(file);
/* 143 */       case 5: return new ConstantLong(file);
/* 144 */       case 6: return new ConstantDouble(file);
/* 145 */       case 12: return new ConstantNameAndType(file);
/* 146 */       case 1: return new ConstantUtf8(file); }
/*     */     
/* 148 */     throw new ClassFormatException("Invalid byte tag in constant pool: " + b);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/Constant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */