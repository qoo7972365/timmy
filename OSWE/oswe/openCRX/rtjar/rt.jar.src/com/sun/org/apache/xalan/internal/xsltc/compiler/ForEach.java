/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.BranchHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.GOTO;
/*     */ import com.sun.org.apache.bcel.internal.generic.IFGT;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
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
/*     */ final class ForEach
/*     */   extends Instruction
/*     */ {
/*     */   private Expression _select;
/*     */   private Type _type;
/*     */   
/*     */   public void display(int indent) {
/*  56 */     indent(indent);
/*  57 */     Util.println("ForEach");
/*  58 */     indent(indent + 4);
/*  59 */     Util.println("select " + this._select.toString());
/*  60 */     displayContents(indent + 4);
/*     */   }
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  64 */     this._select = parser.parseExpression(this, "select", null);
/*     */     
/*  66 */     parseChildren(parser);
/*     */ 
/*     */     
/*  69 */     if (this._select.isDummy()) {
/*  70 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "select");
/*     */     }
/*     */   }
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/*  75 */     this._type = this._select.typeCheck(stable);
/*     */     
/*  77 */     if (this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType || this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType) {
/*  78 */       this._select = new CastExpr(this._select, Type.NodeSet);
/*  79 */       typeCheckContents(stable);
/*  80 */       return Type.Void;
/*     */     } 
/*  82 */     if (this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSetType || this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType) {
/*  83 */       typeCheckContents(stable);
/*  84 */       return Type.Void;
/*     */     } 
/*  86 */     throw new TypeCheckError(this);
/*     */   }
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/*  90 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  91 */     InstructionList il = methodGen.getInstructionList();
/*     */ 
/*     */     
/*  94 */     il.append(methodGen.loadCurrentNode());
/*  95 */     il.append(methodGen.loadIterator());
/*     */ 
/*     */     
/*  98 */     Vector<SyntaxTreeNode> sortObjects = new Vector();
/*  99 */     Iterator<SyntaxTreeNode> children = elements();
/* 100 */     while (children.hasNext()) {
/* 101 */       SyntaxTreeNode child = children.next();
/* 102 */       if (child instanceof Sort) {
/* 103 */         sortObjects.addElement(child);
/*     */       }
/*     */     } 
/*     */     
/* 107 */     if (this._type != null && this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType) {
/*     */       
/* 109 */       il.append(methodGen.loadDOM());
/*     */ 
/*     */       
/* 112 */       if (sortObjects.size() > 0) {
/* 113 */         ErrorMsg msg = new ErrorMsg("RESULT_TREE_SORT_ERR", this);
/* 114 */         getParser().reportError(4, msg);
/*     */       } 
/*     */ 
/*     */       
/* 118 */       this._select.translate(classGen, methodGen);
/*     */       
/* 120 */       this._type.translateTo(classGen, methodGen, Type.NodeSet);
/*     */       
/* 122 */       il.append(SWAP);
/* 123 */       il.append(methodGen.storeDOM());
/*     */     }
/*     */     else {
/*     */       
/* 127 */       if (sortObjects.size() > 0) {
/* 128 */         Sort.translateSortIterator(classGen, methodGen, this._select, (Vector)sortObjects);
/*     */       }
/*     */       else {
/*     */         
/* 132 */         this._select.translate(classGen, methodGen);
/*     */       } 
/*     */       
/* 135 */       if (!(this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType)) {
/* 136 */         il.append(methodGen.loadContextNode());
/* 137 */         il.append(methodGen.setStartNode());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 143 */     il.append(methodGen.storeIterator());
/*     */ 
/*     */     
/* 146 */     initializeVariables(classGen, methodGen);
/*     */     
/* 148 */     BranchHandle nextNode = il.append(new GOTO(null));
/* 149 */     InstructionHandle loop = il.append(NOP);
/*     */     
/* 151 */     translateContents(classGen, methodGen);
/*     */     
/* 153 */     nextNode.setTarget(il.append(methodGen.loadIterator()));
/* 154 */     il.append(methodGen.nextNode());
/* 155 */     il.append(DUP);
/* 156 */     il.append(methodGen.storeCurrentNode());
/* 157 */     il.append(new IFGT(loop));
/*     */ 
/*     */     
/* 160 */     if (this._type != null && this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType) {
/* 161 */       il.append(methodGen.storeDOM());
/*     */     }
/*     */ 
/*     */     
/* 165 */     il.append(methodGen.storeIterator());
/* 166 */     il.append(methodGen.storeCurrentNode());
/*     */   }
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
/*     */   public void initializeVariables(ClassGenerator classGen, MethodGenerator methodGen) {
/* 187 */     int n = elementCount();
/* 188 */     for (int i = 0; i < n; i++) {
/* 189 */       SyntaxTreeNode child = getContents().get(i);
/* 190 */       if (child instanceof Variable) {
/* 191 */         Variable var = (Variable)child;
/* 192 */         var.initialize(classGen, methodGen);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/ForEach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */