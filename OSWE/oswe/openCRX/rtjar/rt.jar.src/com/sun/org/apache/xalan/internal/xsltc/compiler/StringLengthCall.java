/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*    */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
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
/*    */ final class StringLengthCall
/*    */   extends FunctionCall
/*    */ {
/*    */   public StringLengthCall(QName fname, Vector arguments) {
/* 41 */     super(fname, arguments);
/*    */   }
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 45 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 46 */     InstructionList il = methodGen.getInstructionList();
/* 47 */     if (argumentCount() > 0) {
/* 48 */       argument().translate(classGen, methodGen);
/*    */     } else {
/*    */       
/* 51 */       il.append(methodGen.loadContextNode());
/* 52 */       Type.Node.translateTo(classGen, methodGen, Type.String);
/*    */     } 
/* 54 */     il.append(new INVOKESTATIC(cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "getStringLength", "(Ljava/lang/String;)I")));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/StringLengthCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */