/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.BranchHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.GOTO;
/*     */ import com.sun.org.apache.bcel.internal.generic.IFEQ;
/*     */ import com.sun.org.apache.bcel.internal.generic.IFGE;
/*     */ import com.sun.org.apache.bcel.internal.generic.IFGT;
/*     */ import com.sun.org.apache.bcel.internal.generic.ILOAD;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.ISTORE;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
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
/*     */ final class Key
/*     */   extends TopLevelElement
/*     */ {
/*     */   private QName _name;
/*     */   private Pattern _match;
/*     */   private Expression _use;
/*     */   private Type _useType;
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  84 */     String name = getAttribute("name");
/*  85 */     if (!XML11Char.isXML11ValidQName(name)) {
/*  86 */       ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/*  87 */       parser.reportError(3, err);
/*     */     } 
/*     */ 
/*     */     
/*  91 */     this._name = parser.getQNameIgnoreDefaultNs(name);
/*  92 */     getSymbolTable().addKey(this._name, this);
/*     */     
/*  94 */     this._match = parser.parsePattern(this, "match", null);
/*  95 */     this._use = parser.parseExpression(this, "use", null);
/*     */ 
/*     */     
/*  98 */     if (this._name == null) {
/*  99 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
/*     */       return;
/*     */     } 
/* 102 */     if (this._match.isDummy()) {
/* 103 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "match");
/*     */       return;
/*     */     } 
/* 106 */     if (this._use.isDummy()) {
/* 107 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "use");
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 117 */     return this._name.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 122 */     this._match.typeCheck(stable);
/*     */ 
/*     */     
/* 125 */     this._useType = this._use.typeCheck(stable);
/* 126 */     if (!(this._useType instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringType) && !(this._useType instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSetType))
/*     */     {
/*     */       
/* 129 */       this._use = new CastExpr(this._use, Type.String);
/*     */     }
/*     */     
/* 132 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void traverseNodeSet(ClassGenerator classGen, MethodGenerator methodGen, int buildKeyIndex) {
/* 143 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 144 */     InstructionList il = methodGen.getInstructionList();
/*     */ 
/*     */     
/* 147 */     int getNodeValue = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getStringValueX", "(I)Ljava/lang/String;");
/*     */ 
/*     */ 
/*     */     
/* 151 */     int getNodeIdent = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getNodeIdent", "(I)I");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     int keyDom = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "setKeyIndexDom", "(Ljava/lang/String;Lcom/sun/org/apache/xalan/internal/xsltc/DOM;)V");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     LocalVariableGen parentNode = methodGen.addLocalVariable("parentNode", 
/* 166 */         Util.getJCRefType("I"), null, null);
/*     */ 
/*     */ 
/*     */     
/* 170 */     parentNode.setStart(il.append(new ISTORE(parentNode.getIndex())));
/*     */ 
/*     */     
/* 173 */     il.append(methodGen.loadCurrentNode());
/* 174 */     il.append(methodGen.loadIterator());
/*     */ 
/*     */     
/* 177 */     this._use.translate(classGen, methodGen);
/* 178 */     this._use.startIterator(classGen, methodGen);
/* 179 */     il.append(methodGen.storeIterator());
/*     */     
/* 181 */     BranchHandle nextNode = il.append(new GOTO(null));
/* 182 */     InstructionHandle loop = il.append(NOP);
/*     */ 
/*     */     
/* 185 */     il.append(classGen.loadTranslet());
/* 186 */     il.append(new PUSH(cpg, this._name.toString()));
/* 187 */     parentNode.setEnd(il.append(new ILOAD(parentNode.getIndex())));
/*     */ 
/*     */     
/* 190 */     il.append(methodGen.loadDOM());
/* 191 */     il.append(methodGen.loadCurrentNode());
/* 192 */     il.append(new INVOKEINTERFACE(getNodeValue, 2));
/*     */ 
/*     */     
/* 195 */     il.append(new INVOKEVIRTUAL(buildKeyIndex));
/*     */     
/* 197 */     il.append(classGen.loadTranslet());
/* 198 */     il.append(new PUSH(cpg, getName()));
/* 199 */     il.append(methodGen.loadDOM());
/* 200 */     il.append(new INVOKEVIRTUAL(keyDom));
/*     */     
/* 202 */     nextNode.setTarget(il.append(methodGen.loadIterator()));
/* 203 */     il.append(methodGen.nextNode());
/*     */     
/* 205 */     il.append(DUP);
/* 206 */     il.append(methodGen.storeCurrentNode());
/* 207 */     il.append(new IFGE(loop));
/*     */ 
/*     */     
/* 210 */     il.append(methodGen.storeIterator());
/* 211 */     il.append(methodGen.storeCurrentNode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 220 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 221 */     InstructionList il = methodGen.getInstructionList();
/* 222 */     int current = methodGen.getLocalIndex("current");
/*     */ 
/*     */     
/* 225 */     int key = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "buildKeyIndex", "(Ljava/lang/String;ILjava/lang/String;)V");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     int keyDom = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "setKeyIndexDom", "(Ljava/lang/String;Lcom/sun/org/apache/xalan/internal/xsltc/DOM;)V");
/*     */ 
/*     */ 
/*     */     
/* 234 */     int getNodeIdent = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getNodeIdent", "(I)I");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     int git = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getAxisIterator", "(I)Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*     */ 
/*     */ 
/*     */     
/* 243 */     il.append(methodGen.loadCurrentNode());
/* 244 */     il.append(methodGen.loadIterator());
/*     */ 
/*     */     
/* 247 */     il.append(methodGen.loadDOM());
/* 248 */     il.append(new PUSH(cpg, 4));
/* 249 */     il.append(new INVOKEINTERFACE(git, 2));
/*     */ 
/*     */     
/* 252 */     il.append(methodGen.loadCurrentNode());
/* 253 */     il.append(methodGen.setStartNode());
/* 254 */     il.append(methodGen.storeIterator());
/*     */ 
/*     */     
/* 257 */     BranchHandle nextNode = il.append(new GOTO(null));
/* 258 */     InstructionHandle loop = il.append(NOP);
/*     */ 
/*     */     
/* 261 */     il.append(methodGen.loadCurrentNode());
/* 262 */     this._match.translate(classGen, methodGen);
/* 263 */     this._match.synthesize(classGen, methodGen);
/* 264 */     BranchHandle skipNode = il.append(new IFEQ(null));
/*     */ 
/*     */     
/* 267 */     if (this._useType instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSetType) {
/*     */       
/* 269 */       il.append(methodGen.loadCurrentNode());
/* 270 */       traverseNodeSet(classGen, methodGen, key);
/*     */     } else {
/*     */       
/* 273 */       il.append(classGen.loadTranslet());
/* 274 */       il.append(DUP);
/* 275 */       il.append(new PUSH(cpg, this._name.toString()));
/* 276 */       il.append(DUP_X1);
/* 277 */       il.append(methodGen.loadCurrentNode());
/* 278 */       this._use.translate(classGen, methodGen);
/* 279 */       il.append(new INVOKEVIRTUAL(key));
/*     */       
/* 281 */       il.append(methodGen.loadDOM());
/* 282 */       il.append(new INVOKEVIRTUAL(keyDom));
/*     */     } 
/*     */ 
/*     */     
/* 286 */     InstructionHandle skip = il.append(NOP);
/*     */     
/* 288 */     il.append(methodGen.loadIterator());
/* 289 */     il.append(methodGen.nextNode());
/* 290 */     il.append(DUP);
/* 291 */     il.append(methodGen.storeCurrentNode());
/* 292 */     il.append(new IFGT(loop));
/*     */ 
/*     */     
/* 295 */     il.append(methodGen.storeIterator());
/* 296 */     il.append(methodGen.storeCurrentNode());
/*     */     
/* 298 */     nextNode.setTarget(skip);
/* 299 */     skipNode.setTarget(skip);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */