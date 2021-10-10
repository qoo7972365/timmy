/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import jdk.internal.org.objectweb.asm.Opcodes;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InvokeDynamicInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SourceInterpreter
/*     */   extends Interpreter<SourceValue>
/*     */   implements Opcodes
/*     */ {
/*     */   public SourceInterpreter() {
/*  82 */     super(327680);
/*     */   }
/*     */   
/*     */   protected SourceInterpreter(int paramInt) {
/*  86 */     super(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public SourceValue newValue(Type paramType) {
/*  91 */     if (paramType == Type.VOID_TYPE) {
/*  92 */       return null;
/*     */     }
/*  94 */     return new SourceValue((paramType == null) ? 1 : paramType.getSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public SourceValue newOperation(AbstractInsnNode paramAbstractInsnNode) {
/*     */     Object object;
/* 100 */     switch (paramAbstractInsnNode.getOpcode())
/*     */     { case 9:
/*     */       case 10:
/*     */       case 14:
/*     */       case 15:
/* 105 */         i = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 117 */         return new SourceValue(i, paramAbstractInsnNode);case 18: object = ((LdcInsnNode)paramAbstractInsnNode).cst; i = (object instanceof Long || object instanceof Double) ? 2 : 1; return new SourceValue(i, paramAbstractInsnNode);case 178: i = Type.getType(((FieldInsnNode)paramAbstractInsnNode).desc).getSize(); return new SourceValue(i, paramAbstractInsnNode); }  int i = 1; return new SourceValue(i, paramAbstractInsnNode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue copyOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue) {
/* 123 */     return new SourceValue(paramSourceValue.getSize(), paramAbstractInsnNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue unaryOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue) {
/* 130 */     switch (paramAbstractInsnNode.getOpcode())
/*     */     { case 117:
/*     */       case 119:
/*     */       case 133:
/*     */       case 135:
/*     */       case 138:
/*     */       case 140:
/*     */       case 141:
/*     */       case 143:
/* 139 */         i = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 147 */         return new SourceValue(i, paramAbstractInsnNode);case 180: i = Type.getType(((FieldInsnNode)paramAbstractInsnNode).desc).getSize(); return new SourceValue(i, paramAbstractInsnNode); }  int i = 1; return new SourceValue(i, paramAbstractInsnNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue binaryOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue1, SourceValue paramSourceValue2) {
/* 154 */     switch (paramAbstractInsnNode.getOpcode())
/*     */     { case 47:
/*     */       case 49:
/*     */       case 97:
/*     */       case 99:
/*     */       case 101:
/*     */       case 103:
/*     */       case 105:
/*     */       case 107:
/*     */       case 109:
/*     */       case 111:
/*     */       case 113:
/*     */       case 115:
/*     */       case 121:
/*     */       case 123:
/*     */       case 125:
/*     */       case 127:
/*     */       case 129:
/*     */       case 131:
/* 173 */         b = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 178 */         return new SourceValue(b, paramAbstractInsnNode); }  byte b = 1; return new SourceValue(b, paramAbstractInsnNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue ternaryOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue1, SourceValue paramSourceValue2, SourceValue paramSourceValue3) {
/* 185 */     return new SourceValue(1, paramAbstractInsnNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue naryOperation(AbstractInsnNode paramAbstractInsnNode, List<? extends SourceValue> paramList) {
/* 192 */     int i, j = paramAbstractInsnNode.getOpcode();
/* 193 */     if (j == 197) {
/* 194 */       i = 1;
/*     */     } else {
/* 196 */       String str = (j == 186) ? ((InvokeDynamicInsnNode)paramAbstractInsnNode).desc : ((MethodInsnNode)paramAbstractInsnNode).desc;
/*     */       
/* 198 */       i = Type.getReturnType(str).getSize();
/*     */     } 
/* 200 */     return new SourceValue(i, paramAbstractInsnNode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnOperation(AbstractInsnNode paramAbstractInsnNode, SourceValue paramSourceValue1, SourceValue paramSourceValue2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue merge(SourceValue paramSourceValue1, SourceValue paramSourceValue2) {
/* 210 */     if (paramSourceValue1.insns instanceof SmallSet && paramSourceValue2.insns instanceof SmallSet) {
/*     */       
/* 212 */       Set<AbstractInsnNode> set = ((SmallSet)paramSourceValue1.insns).union((SmallSet)paramSourceValue2.insns);
/* 213 */       if (set == paramSourceValue1.insns && paramSourceValue1.size == paramSourceValue2.size) {
/* 214 */         return paramSourceValue1;
/*     */       }
/* 216 */       return new SourceValue(Math.min(paramSourceValue1.size, paramSourceValue2.size), set);
/*     */     } 
/*     */     
/* 219 */     if (paramSourceValue1.size != paramSourceValue2.size || !paramSourceValue1.insns.containsAll(paramSourceValue2.insns)) {
/* 220 */       HashSet<AbstractInsnNode> hashSet = new HashSet();
/* 221 */       hashSet.addAll(paramSourceValue1.insns);
/* 222 */       hashSet.addAll(paramSourceValue2.insns);
/* 223 */       return new SourceValue(Math.min(paramSourceValue1.size, paramSourceValue2.size), hashSet);
/*     */     } 
/* 225 */     return paramSourceValue1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/SourceInterpreter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */