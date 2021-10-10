/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.classfile.LineNumber;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineNumberGen
/*     */   implements InstructionTargeter, Cloneable, Serializable
/*     */ {
/*     */   private InstructionHandle ih;
/*     */   private int src_line;
/*     */   
/*     */   public LineNumberGen(InstructionHandle ih, int src_line) {
/*  83 */     setInstruction(ih);
/*  84 */     setSourceLine(src_line);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsTarget(InstructionHandle ih) {
/*  92 */     return (this.ih == ih);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
/* 101 */     if (old_ih != this.ih) {
/* 102 */       throw new ClassGenException("Not targeting " + old_ih + ", but " + this.ih + "}");
/*     */     }
/* 104 */     setInstruction(new_ih);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineNumber getLineNumber() {
/* 114 */     return new LineNumber(this.ih.getPosition(), this.src_line);
/*     */   }
/*     */   
/*     */   public final void setInstruction(InstructionHandle ih) {
/* 118 */     BranchInstruction.notifyTargetChanging(this.ih, this);
/* 119 */     this.ih = ih;
/* 120 */     BranchInstruction.notifyTargetChanged(this.ih, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 126 */       return super.clone();
/* 127 */     } catch (CloneNotSupportedException e) {
/* 128 */       System.err.println(e);
/* 129 */       return null;
/*     */     } 
/*     */   }
/*     */   
/* 133 */   public InstructionHandle getInstruction() { return this.ih; }
/* 134 */   public void setSourceLine(int src_line) { this.src_line = src_line; } public int getSourceLine() {
/* 135 */     return this.src_line;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/LineNumberGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */