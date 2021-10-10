/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.Map;
/*     */ import jdk.internal.org.objectweb.asm.Label;
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
/*     */ public class LabelNode
/*     */   extends AbstractInsnNode
/*     */ {
/*     */   private Label label;
/*     */   
/*     */   public LabelNode() {
/*  74 */     super(-1);
/*     */   }
/*     */   
/*     */   public LabelNode(Label paramLabel) {
/*  78 */     super(-1);
/*  79 */     this.label = paramLabel;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/*  84 */     return 8;
/*     */   }
/*     */   
/*     */   public Label getLabel() {
/*  88 */     if (this.label == null) {
/*  89 */       this.label = new Label();
/*     */     }
/*  91 */     return this.label;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor paramMethodVisitor) {
/*  96 */     paramMethodVisitor.visitLabel(getLabel());
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
/* 101 */     return paramMap.get(this);
/*     */   }
/*     */   
/*     */   public void resetLabel() {
/* 105 */     this.label = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/LabelNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */