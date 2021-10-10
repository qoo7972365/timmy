/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*    */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
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
/*    */ final class IntExpr
/*    */   extends Expression
/*    */ {
/*    */   private final int _value;
/*    */   
/*    */   public IntExpr(int value) {
/* 42 */     this._value = value;
/*    */   }
/*    */   
/*    */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 46 */     return this._type = Type.Int;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 50 */     return "int-expr(" + this._value + ')';
/*    */   }
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 54 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 55 */     InstructionList il = methodGen.getInstructionList();
/* 56 */     il.append(new PUSH(cpg, this._value));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/IntExpr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */