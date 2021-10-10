/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*    */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class NameCall
/*    */   extends NameBase
/*    */ {
/*    */   public NameCall(QName fname) {
/* 45 */     super(fname);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NameCall(QName fname, Vector arguments) {
/* 52 */     super(fname, arguments);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 59 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 60 */     InstructionList il = methodGen.getInstructionList();
/*    */     
/* 62 */     int getName = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getNodeNameX", "(I)Ljava/lang/String;");
/*    */ 
/*    */     
/* 65 */     super.translate(classGen, methodGen);
/* 66 */     il.append(new INVOKEINTERFACE(getName, 2));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/NameCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */