/*    */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*    */ 
/*    */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnalyzerException
/*    */   extends Exception
/*    */ {
/*    */   public final AbstractInsnNode node;
/*    */   
/*    */   public AnalyzerException(AbstractInsnNode paramAbstractInsnNode, String paramString) {
/* 75 */     super(paramString);
/* 76 */     this.node = paramAbstractInsnNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public AnalyzerException(AbstractInsnNode paramAbstractInsnNode, String paramString, Throwable paramThrowable) {
/* 81 */     super(paramString, paramThrowable);
/* 82 */     this.node = paramAbstractInsnNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public AnalyzerException(AbstractInsnNode paramAbstractInsnNode, String paramString, Object paramObject, Value paramValue) {
/* 87 */     super(((paramString == null) ? "Expected " : (paramString + ": expected ")) + paramObject + ", but found " + paramValue);
/*    */     
/* 89 */     this.node = paramAbstractInsnNode;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/AnalyzerException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */