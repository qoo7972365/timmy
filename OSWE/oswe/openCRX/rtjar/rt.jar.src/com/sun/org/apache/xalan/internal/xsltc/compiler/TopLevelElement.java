/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
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
/*    */ class TopLevelElement
/*    */   extends SyntaxTreeNode
/*    */ {
/* 42 */   protected Vector _dependencies = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 48 */     return typeCheckContents(stable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 56 */     ErrorMsg msg = new ErrorMsg("NOT_IMPLEMENTED_ERR", getClass(), this);
/* 57 */     getParser().reportError(2, msg);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InstructionList compile(ClassGenerator classGen, MethodGenerator methodGen) {
/* 66 */     InstructionList save = methodGen.getInstructionList(); InstructionList result;
/* 67 */     methodGen.setInstructionList(result = new InstructionList());
/* 68 */     translate(classGen, methodGen);
/* 69 */     methodGen.setInstructionList(save);
/* 70 */     return result;
/*    */   }
/*    */   
/*    */   public void display(int indent) {
/* 74 */     indent(indent);
/* 75 */     Util.println("TopLevelElement");
/* 76 */     displayContents(indent + 4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addDependency(TopLevelElement other) {
/* 84 */     if (this._dependencies == null) {
/* 85 */       this._dependencies = new Vector();
/*    */     }
/* 87 */     if (!this._dependencies.contains(other)) {
/* 88 */       this._dependencies.addElement(other);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector getDependencies() {
/* 97 */     return this._dependencies;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/TopLevelElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */