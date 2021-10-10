/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.Instruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.NEW;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.bcel.internal.generic.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
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
/*     */ 
/*     */ 
/*     */ class VariableBase
/*     */   extends TopLevelElement
/*     */ {
/*     */   protected QName _name;
/*     */   protected String _escapedName;
/*     */   protected Type _type;
/*     */   protected boolean _isLocal;
/*     */   protected LocalVariableGen _local;
/*     */   protected Instruction _loadInstruction;
/*     */   protected Instruction _storeInstruction;
/*     */   protected Expression _select;
/*     */   protected String select;
/*  63 */   protected Vector<VariableRefBase> _refs = new Vector<>(2);
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _ignore = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {
/*  72 */     this._ignore = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addReference(VariableRefBase vref) {
/*  80 */     this._refs.addElement(vref);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyReferences(VariableBase var) {
/*  90 */     int size = this._refs.size();
/*  91 */     for (int i = 0; i < size; i++) {
/*  92 */       var.addReference(this._refs.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mapRegister(MethodGenerator methodGen) {
/* 100 */     if (this._local == null) {
/* 101 */       InstructionList il = methodGen.getInstructionList();
/* 102 */       String name = getEscapedName();
/* 103 */       Type varType = this._type.toJCType();
/* 104 */       this._local = methodGen.addLocalVariable2(name, varType, il.getEnd());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unmapRegister(ClassGenerator classGen, MethodGenerator methodGen) {
/* 113 */     if (this._local != null) {
/* 114 */       if (this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType) {
/* 115 */         ConstantPoolGen cpg = classGen.getConstantPool();
/* 116 */         InstructionList il = methodGen.getInstructionList();
/* 117 */         if (classGen.getStylesheet().callsNodeset() && classGen.getDOMClass().equals("com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM")) {
/* 118 */           int removeDA = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM", "removeDOMAdapter", "(Lcom/sun/org/apache/xalan/internal/xsltc/dom/DOMAdapter;)V");
/* 119 */           il.append(methodGen.loadDOM());
/* 120 */           il.append(new CHECKCAST(cpg.addClass("com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM")));
/* 121 */           il.append(loadInstruction());
/* 122 */           il.append(new CHECKCAST(cpg.addClass("com/sun/org/apache/xalan/internal/xsltc/dom/DOMAdapter")));
/* 123 */           il.append(new INVOKEVIRTUAL(removeDA));
/*     */         } 
/* 125 */         int release = cpg.addInterfaceMethodref("com/sun/org/apache/xalan/internal/xsltc/DOM", "release", "()V");
/* 126 */         il.append(loadInstruction());
/* 127 */         il.append(new INVOKEINTERFACE(release, 1));
/*     */       } 
/*     */       
/* 130 */       this._local.setEnd(methodGen.getInstructionList().getEnd());
/* 131 */       methodGen.removeLocalVariable(this._local);
/* 132 */       this._refs = null;
/* 133 */       this._local = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instruction loadInstruction() {
/* 142 */     if (this._loadInstruction == null) {
/* 143 */       this._loadInstruction = this._type.LOAD(this._local.getIndex());
/*     */     }
/* 145 */     return this._loadInstruction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instruction storeInstruction() {
/* 153 */     if (this._storeInstruction == null) {
/* 154 */       this._storeInstruction = this._type.STORE(this._local.getIndex());
/*     */     }
/* 156 */     return this._storeInstruction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression getExpression() {
/* 163 */     return this._select;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 170 */     return "variable(" + this._name + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void display(int indent) {
/* 177 */     indent(indent);
/* 178 */     System.out.println("Variable " + this._name);
/* 179 */     if (this._select != null) {
/* 180 */       indent(indent + 4);
/* 181 */       System.out.println("select " + this._select.toString());
/*     */     } 
/* 183 */     displayContents(indent + 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 190 */     return this._type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 198 */     return this._name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEscapedName() {
/* 205 */     return this._escapedName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(QName name) {
/* 212 */     this._name = name;
/* 213 */     this._escapedName = Util.escape(name.getStringRep());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocal() {
/* 220 */     return this._isLocal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/* 228 */     String name = getAttribute("name");
/*     */     
/* 230 */     if (name.length() > 0) {
/* 231 */       if (!XML11Char.isXML11ValidQName(name)) {
/* 232 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/* 233 */         parser.reportError(3, err);
/*     */       } 
/* 235 */       setName(parser.getQNameIgnoreDefaultNs(name));
/*     */     } else {
/*     */       
/* 238 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
/*     */     } 
/*     */     
/* 241 */     VariableBase other = parser.lookupVariable(this._name);
/* 242 */     if (other != null && other.getParent() == getParent()) {
/* 243 */       reportError(this, parser, "VARIABLE_REDEF_ERR", name);
/*     */     }
/*     */     
/* 246 */     this.select = getAttribute("select");
/* 247 */     if (this.select.length() > 0) {
/* 248 */       this._select = getParser().parseExpression(this, "select", null);
/* 249 */       if (this._select.isDummy()) {
/* 250 */         reportError(this, parser, "REQUIRED_ATTR_ERR", "select");
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     parseChildren(parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateValue(ClassGenerator classGen, MethodGenerator methodGen) {
/* 266 */     if (this._select != null) {
/* 267 */       this._select.translate(classGen, methodGen);
/*     */ 
/*     */       
/* 270 */       if (this._select.getType() instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSetType) {
/* 271 */         ConstantPoolGen cpg = classGen.getConstantPool();
/* 272 */         InstructionList il = methodGen.getInstructionList();
/*     */         
/* 274 */         int initCNI = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.dom.CachedNodeListIterator", "<init>", "(Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;)V");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 279 */         il.append(new NEW(cpg.addClass("com.sun.org.apache.xalan.internal.xsltc.dom.CachedNodeListIterator")));
/* 280 */         il.append(DUP_X1);
/* 281 */         il.append(SWAP);
/*     */         
/* 283 */         il.append(new INVOKESPECIAL(initCNI));
/*     */       } 
/* 285 */       this._select.startIterator(classGen, methodGen);
/*     */     
/*     */     }
/* 288 */     else if (hasContents()) {
/* 289 */       compileResultTree(classGen, methodGen);
/*     */     }
/*     */     else {
/*     */       
/* 293 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 294 */       InstructionList il = methodGen.getInstructionList();
/* 295 */       il.append(new PUSH(cpg, ""));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/VariableBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */