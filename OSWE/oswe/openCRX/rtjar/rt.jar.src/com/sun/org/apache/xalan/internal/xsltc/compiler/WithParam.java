/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*     */ import com.sun.org.apache.bcel.internal.generic.ASTORE;
/*     */ import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import com.sun.org.apache.xml.internal.utils.XML11Char;
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
/*     */ final class WithParam
/*     */   extends Instruction
/*     */ {
/*     */   private QName _name;
/*     */   protected String _escapedName;
/*     */   private Expression _select;
/*     */   private LocalVariableGen _domAdapter;
/*     */   private boolean _doParameterOptimization = false;
/*     */   
/*     */   public void display(int indent) {
/*  80 */     indent(indent);
/*  81 */     Util.println("with-param " + this._name);
/*  82 */     if (this._select != null) {
/*  83 */       indent(indent + 4);
/*  84 */       Util.println("select " + this._select.toString());
/*     */     } 
/*  86 */     displayContents(indent + 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEscapedName() {
/*  93 */     return this._escapedName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 100 */     return this._name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(QName name) {
/* 107 */     this._name = name;
/* 108 */     this._escapedName = Util.escape(name.getStringRep());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoParameterOptimization(boolean flag) {
/* 115 */     this._doParameterOptimization = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/* 123 */     String name = getAttribute("name");
/* 124 */     if (name.length() > 0) {
/* 125 */       if (!XML11Char.isXML11ValidQName(name)) {
/* 126 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/*     */         
/* 128 */         parser.reportError(3, err);
/*     */       } 
/* 130 */       setName(parser.getQNameIgnoreDefaultNs(name));
/*     */     } else {
/* 132 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
/*     */     } 
/*     */     
/* 135 */     String select = getAttribute("select");
/* 136 */     if (select.length() > 0) {
/* 137 */       this._select = parser.parseExpression(this, "select", null);
/*     */     }
/*     */     
/* 140 */     parseChildren(parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 148 */     if (this._select != null) {
/* 149 */       Type tselect = this._select.typeCheck(stable);
/* 150 */       if (!(tselect instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType)) {
/* 151 */         this._select = new CastExpr(this._select, Type.Reference);
/*     */       }
/*     */     } else {
/* 154 */       typeCheckContents(stable);
/*     */     } 
/* 156 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateValue(ClassGenerator classGen, MethodGenerator methodGen) {
/* 167 */     if (this._select != null) {
/* 168 */       this._select.translate(classGen, methodGen);
/* 169 */       this._select.startIterator(classGen, methodGen);
/*     */     
/*     */     }
/* 172 */     else if (hasContents()) {
/* 173 */       InstructionList il = methodGen.getInstructionList();
/* 174 */       compileResultTree(classGen, methodGen);
/* 175 */       this._domAdapter = methodGen.addLocalVariable2("@" + this._escapedName, Type.ResultTree
/* 176 */           .toJCType(), il
/* 177 */           .getEnd());
/* 178 */       il.append(DUP);
/* 179 */       il.append(new ASTORE(this._domAdapter.getIndex()));
/*     */     } else {
/*     */       
/* 182 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 183 */       InstructionList il = methodGen.getInstructionList();
/* 184 */       il.append(new PUSH(cpg, ""));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 194 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 195 */     InstructionList il = methodGen.getInstructionList();
/*     */ 
/*     */     
/* 198 */     if (this._doParameterOptimization) {
/* 199 */       translateValue(classGen, methodGen);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 204 */     String name = Util.escape(getEscapedName());
/*     */ 
/*     */     
/* 207 */     il.append(classGen.loadTranslet());
/*     */ 
/*     */     
/* 210 */     il.append(new PUSH(cpg, name));
/*     */     
/* 212 */     translateValue(classGen, methodGen);
/*     */     
/* 214 */     il.append(new PUSH(cpg, false));
/*     */     
/* 216 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "addParameter", "(Ljava/lang/String;Ljava/lang/Object;Z)Ljava/lang/Object;")));
/*     */ 
/*     */     
/* 219 */     il.append(POP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseResultTree(ClassGenerator classGen, MethodGenerator methodGen) {
/* 228 */     if (this._domAdapter != null) {
/* 229 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 230 */       InstructionList il = methodGen.getInstructionList();
/* 231 */       if (classGen.getStylesheet().callsNodeset() && classGen
/* 232 */         .getDOMClass().equals("com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM")) {
/*     */ 
/*     */         
/* 235 */         int removeDA = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM", "removeDOMAdapter", "(Lcom/sun/org/apache/xalan/internal/xsltc/dom/DOMAdapter;)V");
/*     */         
/* 237 */         il.append(methodGen.loadDOM());
/* 238 */         il.append(new CHECKCAST(cpg.addClass("com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM")));
/* 239 */         il.append(new ALOAD(this._domAdapter.getIndex()));
/* 240 */         il.append(new CHECKCAST(cpg.addClass("com/sun/org/apache/xalan/internal/xsltc/dom/DOMAdapter")));
/* 241 */         il.append(new INVOKEVIRTUAL(removeDA));
/*     */       } 
/*     */       
/* 244 */       int release = cpg.addInterfaceMethodref("com/sun/org/apache/xalan/internal/xsltc/DOM", "release", "()V");
/* 245 */       il.append(new ALOAD(this._domAdapter.getIndex()));
/* 246 */       il.append(new INVOKEINTERFACE(release, 1));
/* 247 */       this._domAdapter.setEnd(il.getEnd());
/* 248 */       methodGen.removeLocalVariable(this._domAdapter);
/* 249 */       this._domAdapter = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/WithParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */