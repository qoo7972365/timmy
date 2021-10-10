/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*    */ import java.util.Vector;
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
/*    */ final class FloorCall
/*    */   extends FunctionCall
/*    */ {
/*    */   public FloorCall(QName fname, Vector arguments) {
/* 38 */     super(fname, arguments);
/*    */   }
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 42 */     argument().translate(classGen, methodGen);
/* 43 */     methodGen.getInstructionList()
/* 44 */       .append(new INVOKESTATIC(classGen.getConstantPool()
/* 45 */           .addMethodref("java.lang.Math", "floor", "(D)D")));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/FloorCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */