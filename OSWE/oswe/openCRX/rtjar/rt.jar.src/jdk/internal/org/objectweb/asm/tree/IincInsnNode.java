/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.Map;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IincInsnNode
/*     */   extends AbstractInsnNode
/*     */ {
/*     */   public int var;
/*     */   public int incr;
/*     */   
/*     */   public IincInsnNode(int paramInt1, int paramInt2) {
/*  92 */     super(132);
/*  93 */     this.var = paramInt1;
/*  94 */     this.incr = paramInt2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/*  99 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor paramMethodVisitor) {
/* 104 */     paramMethodVisitor.visitIincInsn(this.var, this.incr);
/* 105 */     acceptAnnotations(paramMethodVisitor);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
/* 110 */     return (new IincInsnNode(this.var, this.incr)).cloneAnnotations(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/IincInsnNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */