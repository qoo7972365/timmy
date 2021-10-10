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
/*     */ public class LookupSwitchInsnNode
/*     */   extends AbstractInsnNode
/*     */ {
/*     */   public LabelNode dflt;
/*     */   public List<Integer> keys;
/*     */   public List<LabelNode> labels;
/*     */   
/*     */   public LookupSwitchInsnNode(LabelNode paramLabelNode, int[] paramArrayOfint, LabelNode[] paramArrayOfLabelNode) {
/* 106 */     super(171);
/* 107 */     this.dflt = paramLabelNode;
/* 108 */     this.keys = new ArrayList<>((paramArrayOfint == null) ? 0 : paramArrayOfint.length);
/* 109 */     this.labels = new ArrayList<>((paramArrayOfLabelNode == null) ? 0 : paramArrayOfLabelNode.length);
/*     */     
/* 111 */     if (paramArrayOfint != null) {
/* 112 */       for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 113 */         this.keys.add(Integer.valueOf(paramArrayOfint[b]));
/*     */       }
/*     */     }
/* 116 */     if (paramArrayOfLabelNode != null) {
/* 117 */       this.labels.addAll(Arrays.asList(paramArrayOfLabelNode));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 123 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor paramMethodVisitor) {
/* 128 */     int[] arrayOfInt = new int[this.keys.size()];
/* 129 */     for (byte b1 = 0; b1 < arrayOfInt.length; b1++) {
/* 130 */       arrayOfInt[b1] = ((Integer)this.keys.get(b1)).intValue();
/*     */     }
/* 132 */     Label[] arrayOfLabel = new Label[this.labels.size()];
/* 133 */     for (byte b2 = 0; b2 < arrayOfLabel.length; b2++) {
/* 134 */       arrayOfLabel[b2] = ((LabelNode)this.labels.get(b2)).getLabel();
/*     */     }
/* 136 */     paramMethodVisitor.visitLookupSwitchInsn(this.dflt.getLabel(), arrayOfInt, arrayOfLabel);
/* 137 */     acceptAnnotations(paramMethodVisitor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
/* 143 */     LookupSwitchInsnNode lookupSwitchInsnNode = new LookupSwitchInsnNode(clone(this.dflt, paramMap), null, clone(this.labels, paramMap));
/* 144 */     lookupSwitchInsnNode.keys.addAll(this.keys);
/* 145 */     return lookupSwitchInsnNode.cloneAnnotations(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/LookupSwitchInsnNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */