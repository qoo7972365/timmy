/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
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
/*    */ final class NumberCall
/*    */   extends FunctionCall
/*    */ {
/*    */   public NumberCall(QName fname, Vector arguments) {
/* 41 */     super(fname, arguments);
/*    */   }
/*    */   
/*    */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 45 */     if (argumentCount() > 0) {
/* 46 */       argument().typeCheck(stable);
/*    */     }
/* 48 */     return this._type = Type.Real;
/*    */   }
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/*    */     Type targ;
/* 52 */     InstructionList il = methodGen.getInstructionList();
/*    */ 
/*    */     
/* 55 */     if (argumentCount() == 0) {
/* 56 */       il.append(methodGen.loadContextNode());
/* 57 */       targ = Type.Node;
/*    */     } else {
/*    */       
/* 60 */       Expression arg = argument();
/* 61 */       arg.translate(classGen, methodGen);
/* 62 */       arg.startIterator(classGen, methodGen);
/* 63 */       targ = arg.getType();
/*    */     } 
/*    */     
/* 66 */     if (!targ.identicalTo(Type.Real))
/* 67 */       targ.translateTo(classGen, methodGen, Type.Real); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/NumberCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */