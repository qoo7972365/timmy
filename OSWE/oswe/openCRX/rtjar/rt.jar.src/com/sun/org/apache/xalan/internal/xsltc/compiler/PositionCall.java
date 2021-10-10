/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*    */ import com.sun.org.apache.bcel.internal.generic.ILOAD;
/*    */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.CompareGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
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
/*    */ final class PositionCall
/*    */   extends FunctionCall
/*    */ {
/*    */   public PositionCall(QName fname) {
/* 43 */     super(fname);
/*    */   }
/*    */   
/*    */   public boolean hasPositionCall() {
/* 47 */     return true;
/*    */   }
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 51 */     InstructionList il = methodGen.getInstructionList();
/*    */     
/* 53 */     if (methodGen instanceof CompareGenerator) {
/* 54 */       il.append(((CompareGenerator)methodGen).loadCurrentNode());
/*    */     }
/* 56 */     else if (methodGen instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator) {
/* 57 */       il.append(new ILOAD(2));
/*    */     } else {
/*    */       
/* 60 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 61 */       int index = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.dtm.DTMAxisIterator", "getPosition", "()I");
/*    */ 
/*    */ 
/*    */       
/* 65 */       il.append(methodGen.loadIterator());
/* 66 */       il.append(new INVOKEINTERFACE(index, 1));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/PositionCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */