/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import java.util.Set;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SourceValue
/*     */   implements Value
/*     */ {
/*     */   public final int size;
/*     */   public final Set<AbstractInsnNode> insns;
/*     */   
/*     */   public SourceValue(int paramInt) {
/*  96 */     this(paramInt, SmallSet.emptySet());
/*     */   }
/*     */   
/*     */   public SourceValue(int paramInt, AbstractInsnNode paramAbstractInsnNode) {
/* 100 */     this.size = paramInt;
/* 101 */     this.insns = new SmallSet<>(paramAbstractInsnNode, null);
/*     */   }
/*     */   
/*     */   public SourceValue(int paramInt, Set<AbstractInsnNode> paramSet) {
/* 105 */     this.size = paramInt;
/* 106 */     this.insns = paramSet;
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 110 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 115 */     if (!(paramObject instanceof SourceValue)) {
/* 116 */       return false;
/*     */     }
/* 118 */     SourceValue sourceValue = (SourceValue)paramObject;
/* 119 */     return (this.size == sourceValue.size && this.insns.equals(sourceValue.insns));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 124 */     return this.insns.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/SourceValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */