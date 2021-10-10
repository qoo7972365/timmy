/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*    */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
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
/*    */ final class GenerateIdCall
/*    */   extends FunctionCall
/*    */ {
/*    */   public GenerateIdCall(QName fname, Vector arguments) {
/* 40 */     super(fname, arguments);
/*    */   }
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 44 */     InstructionList il = methodGen.getInstructionList();
/* 45 */     if (argumentCount() == 0) {
/* 46 */       il.append(methodGen.loadContextNode());
/*    */     } else {
/*    */       
/* 49 */       argument().translate(classGen, methodGen);
/*    */     } 
/* 51 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 52 */     il.append(new INVOKESTATIC(cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "generate_idF", "(I)Ljava/lang/String;")));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/GenerateIdCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */