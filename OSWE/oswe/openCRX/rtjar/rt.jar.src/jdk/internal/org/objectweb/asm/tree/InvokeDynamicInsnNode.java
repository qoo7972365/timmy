/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.Map;
/*     */ import jdk.internal.org.objectweb.asm.Handle;
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
/*     */ public class InvokeDynamicInsnNode
/*     */   extends AbstractInsnNode
/*     */ {
/*     */   public String name;
/*     */   public String desc;
/*     */   public Handle bsm;
/*     */   public Object[] bsmArgs;
/*     */   
/*     */   public InvokeDynamicInsnNode(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/* 108 */     super(186);
/* 109 */     this.name = paramString1;
/* 110 */     this.desc = paramString2;
/* 111 */     this.bsm = paramHandle;
/* 112 */     this.bsmArgs = paramVarArgs;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 117 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor paramMethodVisitor) {
/* 122 */     paramMethodVisitor.visitInvokeDynamicInsn(this.name, this.desc, this.bsm, this.bsmArgs);
/* 123 */     acceptAnnotations(paramMethodVisitor);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap) {
/* 128 */     return (new InvokeDynamicInsnNode(this.name, this.desc, this.bsm, this.bsmArgs))
/* 129 */       .cloneAnnotations(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/InvokeDynamicInsnNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */