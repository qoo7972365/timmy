/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TableSwitchInsnNode
/*     */   extends AbstractInsnNode
/*     */ {
/*     */   public int min;
/*     */   public int max;
/*     */   public LabelNode dflt;
/*     */   public List<LabelNode> labels;
/*     */   
/*     */   public TableSwitchInsnNode(int paramInt1, int paramInt2, LabelNode paramLabelNode, LabelNode... paramVarArgs) {
/* 113 */     super(170);
/* 114 */     this.min = paramInt1;
/* 115 */     this.max = paramInt2;
/* 116 */     this.dflt = paramLabelNode;
/* 117 */     this.labels = new ArrayList<>();
/* 118 */     if (paramVarArgs != null) {
/* 119 */       this.labels.addAll(Arrays.asList(paramVarArgs));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 125 */     return 11;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor paramMethodVisitor) {
/* 130 */     Label[] arrayOfLabel = new Label[this.labels.size()];
/* 131 */     for (byte b = 0; b < arrayOfLabel.length; b++) {
/* 132 */       arrayOfLabel[b] = ((LabelNode)this.labels.get(b)).getLabel();
/*     */     }
/* 134 */     paramMethodVisitor.visitTableSwitchInsn(this.min, this.max, this.dflt.getLabel(), arrayOfLabel);
/* 135 */     acceptAnnotations(paramMethodVisitor);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
/* 140 */     return (new TableSwitchInsnNode(this.min, this.max, clone(this.dflt, paramMap), clone(this.labels, paramMap)))
/* 141 */       .cloneAnnotations(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/TableSwitchInsnNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */