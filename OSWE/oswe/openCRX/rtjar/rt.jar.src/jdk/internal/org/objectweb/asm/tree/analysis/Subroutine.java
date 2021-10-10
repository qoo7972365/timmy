/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LabelNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Subroutine
/*     */ {
/*     */   LabelNode start;
/*     */   boolean[] access;
/*     */   List<JumpInsnNode> callers;
/*     */   
/*     */   private Subroutine() {}
/*     */   
/*     */   Subroutine(LabelNode paramLabelNode, int paramInt, JumpInsnNode paramJumpInsnNode) {
/*  85 */     this.start = paramLabelNode;
/*  86 */     this.access = new boolean[paramInt];
/*  87 */     this.callers = new ArrayList<>();
/*  88 */     this.callers.add(paramJumpInsnNode);
/*     */   }
/*     */   
/*     */   public Subroutine copy() {
/*  92 */     Subroutine subroutine = new Subroutine();
/*  93 */     subroutine.start = this.start;
/*  94 */     subroutine.access = new boolean[this.access.length];
/*  95 */     System.arraycopy(this.access, 0, subroutine.access, 0, this.access.length);
/*  96 */     subroutine.callers = new ArrayList<>(this.callers);
/*  97 */     return subroutine;
/*     */   }
/*     */   
/*     */   public boolean merge(Subroutine paramSubroutine) throws AnalyzerException {
/* 101 */     boolean bool = false; byte b;
/* 102 */     for (b = 0; b < this.access.length; b++) {
/* 103 */       if (paramSubroutine.access[b] && !this.access[b]) {
/* 104 */         this.access[b] = true;
/* 105 */         bool = true;
/*     */       } 
/*     */     } 
/* 108 */     if (paramSubroutine.start == this.start) {
/* 109 */       for (b = 0; b < paramSubroutine.callers.size(); b++) {
/* 110 */         JumpInsnNode jumpInsnNode = paramSubroutine.callers.get(b);
/* 111 */         if (!this.callers.contains(jumpInsnNode)) {
/* 112 */           this.callers.add(jumpInsnNode);
/* 113 */           bool = true;
/*     */         } 
/*     */       } 
/*     */     }
/* 117 */     return bool;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/Subroutine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */