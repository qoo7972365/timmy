/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import com.sun.org.apache.xml.internal.utils.XML11Char;
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
/*     */ final class ApplyTemplates
/*     */   extends Instruction
/*     */ {
/*     */   private Expression _select;
/*  47 */   private Type _type = null;
/*     */   private QName _modeName;
/*     */   private String _functionName;
/*     */   
/*     */   public void display(int indent) {
/*  52 */     indent(indent);
/*  53 */     Util.println("ApplyTemplates");
/*  54 */     indent(indent + 4);
/*  55 */     Util.println("select " + this._select.toString());
/*  56 */     if (this._modeName != null) {
/*  57 */       indent(indent + 4);
/*  58 */       Util.println("mode " + this._modeName);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasWithParams() {
/*  63 */     return hasContents();
/*     */   }
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  67 */     String select = getAttribute("select");
/*  68 */     String mode = getAttribute("mode");
/*     */     
/*  70 */     if (select.length() > 0) {
/*  71 */       this._select = parser.parseExpression(this, "select", null);
/*     */     }
/*     */ 
/*     */     
/*  75 */     if (mode.length() > 0) {
/*  76 */       if (!XML11Char.isXML11ValidQName(mode)) {
/*  77 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", mode, this);
/*  78 */         parser.reportError(3, err);
/*     */       } 
/*  80 */       this._modeName = parser.getQNameIgnoreDefaultNs(mode);
/*     */     } 
/*     */ 
/*     */     
/*  84 */     this
/*  85 */       ._functionName = parser.getTopLevelStylesheet().getMode(this._modeName).functionName();
/*  86 */     parseChildren(parser);
/*     */   }
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/*  90 */     if (this._select != null) {
/*  91 */       this._type = this._select.typeCheck(stable);
/*  92 */       if (this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType || this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType) {
/*  93 */         this._select = new CastExpr(this._select, Type.NodeSet);
/*  94 */         this._type = Type.NodeSet;
/*     */       } 
/*  96 */       if (this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSetType || this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType) {
/*  97 */         typeCheckContents(stable);
/*  98 */         return Type.Void;
/*     */       } 
/* 100 */       throw new TypeCheckError(this);
/*     */     } 
/*     */     
/* 103 */     typeCheckContents(stable);
/* 104 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 113 */     boolean setStartNodeCalled = false;
/* 114 */     Stylesheet stylesheet = classGen.getStylesheet();
/* 115 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 116 */     InstructionList il = methodGen.getInstructionList();
/* 117 */     int current = methodGen.getLocalIndex("current");
/*     */ 
/*     */     
/* 120 */     Vector<Sort> sortObjects = new Vector<>();
/* 121 */     for (SyntaxTreeNode child : getContents()) {
/* 122 */       if (child instanceof Sort) {
/* 123 */         sortObjects.addElement((Sort)child);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 128 */     if (stylesheet.hasLocalParams() || hasContents()) {
/* 129 */       il.append(classGen.loadTranslet());
/* 130 */       int pushFrame = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "pushParamFrame", "()V");
/*     */ 
/*     */       
/* 133 */       il.append(new INVOKEVIRTUAL(pushFrame));
/*     */       
/* 135 */       translateContents(classGen, methodGen);
/*     */     } 
/*     */ 
/*     */     
/* 139 */     il.append(classGen.loadTranslet());
/*     */ 
/*     */     
/* 142 */     if (this._type != null && this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType) {
/*     */       
/* 144 */       if (sortObjects.size() > 0) {
/* 145 */         ErrorMsg err = new ErrorMsg("RESULT_TREE_SORT_ERR", this);
/* 146 */         getParser().reportError(4, err);
/*     */       } 
/*     */       
/* 149 */       this._select.translate(classGen, methodGen);
/*     */       
/* 151 */       this._type.translateTo(classGen, methodGen, Type.NodeSet);
/*     */     } else {
/*     */       
/* 154 */       il.append(methodGen.loadDOM());
/*     */ 
/*     */       
/* 157 */       if (sortObjects.size() > 0) {
/* 158 */         Sort.translateSortIterator(classGen, methodGen, this._select, sortObjects);
/*     */         
/* 160 */         int setStartNode = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.dtm.DTMAxisIterator", "setStartNode", "(I)Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*     */ 
/*     */ 
/*     */         
/* 164 */         il.append(methodGen.loadCurrentNode());
/* 165 */         il.append(new INVOKEINTERFACE(setStartNode, 2));
/* 166 */         setStartNodeCalled = true;
/*     */       
/*     */       }
/* 169 */       else if (this._select == null) {
/* 170 */         Mode.compileGetChildren(classGen, methodGen, current);
/*     */       } else {
/* 172 */         this._select.translate(classGen, methodGen);
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     if (this._select != null && !setStartNodeCalled) {
/* 177 */       this._select.startIterator(classGen, methodGen);
/*     */     }
/*     */ 
/*     */     
/* 181 */     String className = classGen.getStylesheet().getClassName();
/* 182 */     il.append(methodGen.loadHandler());
/* 183 */     String applyTemplatesSig = classGen.getApplyTemplatesSig();
/* 184 */     int applyTemplates = cpg.addMethodref(className, this._functionName, applyTemplatesSig);
/*     */ 
/*     */     
/* 187 */     il.append(new INVOKEVIRTUAL(applyTemplates));
/*     */ 
/*     */     
/* 190 */     for (SyntaxTreeNode child : getContents()) {
/* 191 */       if (child instanceof WithParam) {
/* 192 */         ((WithParam)child).releaseResultTree(classGen, methodGen);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 197 */     if (stylesheet.hasLocalParams() || hasContents()) {
/* 198 */       il.append(classGen.loadTranslet());
/* 199 */       int popFrame = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "popParamFrame", "()V");
/*     */ 
/*     */       
/* 202 */       il.append(new INVOKEVIRTUAL(popFrame));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/ApplyTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */