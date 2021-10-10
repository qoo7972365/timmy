/*     */ package com.sun.org.apache.bcel.internal.classfile;
/*     */ 
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
/*     */ public final class LineNumber
/*     */   implements Cloneable, Node, Serializable
/*     */ {
/*     */   private int start_pc;
/*     */   private int line_number;
/*     */   
/*     */   public LineNumber(LineNumber c) {
/*  80 */     this(c.getStartPC(), c.getLineNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LineNumber(DataInputStream file) throws IOException {
/*  90 */     this(file.readUnsignedShort(), file.readUnsignedShort());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineNumber(int start_pc, int line_number) {
/*  99 */     this.start_pc = start_pc;
/* 100 */     this.line_number = line_number;
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
/* 111 */     v.visitLineNumber(this);
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
/* 122 */     file.writeShort(this.start_pc);
/* 123 */     file.writeShort(this.line_number);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getLineNumber() {
/* 129 */     return this.line_number;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getStartPC() {
/* 134 */     return this.start_pc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setLineNumber(int line_number) {
/* 140 */     this.line_number = line_number;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setStartPC(int start_pc) {
/* 147 */     this.start_pc = start_pc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 154 */     return "LineNumber(" + this.start_pc + ", " + this.line_number + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineNumber copy() {
/*     */     try {
/* 162 */       return (LineNumber)clone();
/* 163 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 165 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/LineNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */