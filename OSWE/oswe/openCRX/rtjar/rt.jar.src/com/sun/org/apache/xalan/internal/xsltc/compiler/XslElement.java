/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*     */ import com.sun.org.apache.bcel.internal.generic.ASTORE;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
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
/*     */ final class XslElement
/*     */   extends Instruction
/*     */ {
/*     */   private String _prefix;
/*     */   private boolean _ignore = false;
/*     */   private boolean _isLiteralName = true;
/*     */   private AttributeValueTemplate _name;
/*     */   private AttributeValueTemplate _namespace;
/*     */   
/*     */   public void display(int indent) {
/*  55 */     indent(indent);
/*  56 */     Util.println("Element " + this._name);
/*  57 */     displayContents(indent + 4);
/*     */   }
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  61 */     SymbolTable stable = parser.getSymbolTable();
/*     */ 
/*     */     
/*  64 */     String name = getAttribute("name");
/*  65 */     if (name == "") {
/*  66 */       ErrorMsg msg = new ErrorMsg("ILLEGAL_ELEM_NAME_ERR", name, this);
/*     */       
/*  68 */       parser.reportError(4, msg);
/*  69 */       parseChildren(parser);
/*  70 */       this._ignore = true;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  75 */     String namespace = getAttribute("namespace");
/*     */ 
/*     */     
/*  78 */     this._isLiteralName = Util.isLiteral(name);
/*  79 */     if (this._isLiteralName) {
/*  80 */       if (!XML11Char.isXML11ValidQName(name)) {
/*  81 */         ErrorMsg msg = new ErrorMsg("ILLEGAL_ELEM_NAME_ERR", name, this);
/*     */         
/*  83 */         parser.reportError(4, msg);
/*  84 */         parseChildren(parser);
/*  85 */         this._ignore = true;
/*     */         
/*     */         return;
/*     */       } 
/*  89 */       QName qname = parser.getQNameSafe(name);
/*  90 */       String prefix = qname.getPrefix();
/*  91 */       String local = qname.getLocalPart();
/*     */       
/*  93 */       if (prefix == null) {
/*  94 */         prefix = "";
/*     */       }
/*     */       
/*  97 */       if (!hasAttribute("namespace")) {
/*  98 */         namespace = lookupNamespace(prefix);
/*  99 */         if (namespace == null) {
/* 100 */           ErrorMsg err = new ErrorMsg("NAMESPACE_UNDEF_ERR", prefix, this);
/*     */           
/* 102 */           parser.reportError(4, err);
/* 103 */           parseChildren(parser);
/* 104 */           this._ignore = true;
/*     */           return;
/*     */         } 
/* 107 */         this._prefix = prefix;
/* 108 */         this._namespace = new AttributeValueTemplate(namespace, parser, this);
/*     */       } else {
/*     */         
/* 111 */         if (prefix == "") {
/* 112 */           if (Util.isLiteral(namespace)) {
/* 113 */             prefix = lookupPrefix(namespace);
/* 114 */             if (prefix == null) {
/* 115 */               prefix = stable.generateNamespacePrefix();
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 120 */           StringBuffer newName = new StringBuffer(prefix);
/* 121 */           if (prefix != "") {
/* 122 */             newName.append(':');
/*     */           }
/* 124 */           name = newName.append(local).toString();
/*     */         } 
/* 126 */         this._prefix = prefix;
/* 127 */         this._namespace = new AttributeValueTemplate(namespace, parser, this);
/*     */       } 
/*     */     } else {
/*     */       
/* 131 */       this._namespace = (namespace == "") ? null : new AttributeValueTemplate(namespace, parser, this);
/*     */     } 
/*     */ 
/*     */     
/* 135 */     this._name = new AttributeValueTemplate(name, parser, this);
/*     */     
/* 137 */     String useSets = getAttribute("use-attribute-sets");
/* 138 */     if (useSets.length() > 0) {
/* 139 */       if (!Util.isValidQNames(useSets)) {
/* 140 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", useSets, this);
/* 141 */         parser.reportError(3, err);
/*     */       } 
/* 143 */       setFirstElement(new UseAttributeSets(useSets, parser));
/*     */     } 
/*     */     
/* 146 */     parseChildren(parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 153 */     if (!this._ignore) {
/* 154 */       this._name.typeCheck(stable);
/* 155 */       if (this._namespace != null) {
/* 156 */         this._namespace.typeCheck(stable);
/*     */       }
/*     */     } 
/* 159 */     typeCheckContents(stable);
/* 160 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateLiteral(ClassGenerator classGen, MethodGenerator methodGen) {
/* 169 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 170 */     InstructionList il = methodGen.getInstructionList();
/*     */     
/* 172 */     if (!this._ignore) {
/* 173 */       il.append(methodGen.loadHandler());
/* 174 */       this._name.translate(classGen, methodGen);
/* 175 */       il.append(DUP2);
/* 176 */       il.append(methodGen.startElement());
/*     */       
/* 178 */       if (this._namespace != null) {
/* 179 */         il.append(methodGen.loadHandler());
/* 180 */         il.append(new PUSH(cpg, this._prefix));
/* 181 */         this._namespace.translate(classGen, methodGen);
/* 182 */         il.append(methodGen.namespace());
/*     */       } 
/*     */     } 
/*     */     
/* 186 */     translateContents(classGen, methodGen);
/*     */     
/* 188 */     if (!this._ignore) {
/* 189 */       il.append(methodGen.endElement());
/*     */     }
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
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 202 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 203 */     InstructionList il = methodGen.getInstructionList();
/*     */ 
/*     */     
/* 206 */     if (this._isLiteralName) {
/* 207 */       translateLiteral(classGen, methodGen);
/*     */       
/*     */       return;
/*     */     } 
/* 211 */     if (!this._ignore) {
/*     */ 
/*     */ 
/*     */       
/* 215 */       LocalVariableGen nameValue = methodGen.addLocalVariable2("nameValue", 
/* 216 */           Util.getJCRefType("Ljava/lang/String;"), null);
/*     */ 
/*     */ 
/*     */       
/* 220 */       this._name.translate(classGen, methodGen);
/* 221 */       nameValue.setStart(il.append(new ASTORE(nameValue.getIndex())));
/* 222 */       il.append(new ALOAD(nameValue.getIndex()));
/*     */ 
/*     */       
/* 225 */       int check = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "checkQName", "(Ljava/lang/String;)V");
/*     */ 
/*     */ 
/*     */       
/* 229 */       il.append(new INVOKESTATIC(check));
/*     */ 
/*     */       
/* 232 */       il.append(methodGen.loadHandler());
/*     */ 
/*     */       
/* 235 */       nameValue.setEnd(il.append(new ALOAD(nameValue.getIndex())));
/*     */       
/* 237 */       if (this._namespace != null) {
/* 238 */         this._namespace.translate(classGen, methodGen);
/*     */       } else {
/*     */         
/* 241 */         il.append(ACONST_NULL);
/*     */       } 
/*     */ 
/*     */       
/* 245 */       il.append(methodGen.loadHandler());
/* 246 */       il.append(methodGen.loadDOM());
/* 247 */       il.append(methodGen.loadCurrentNode());
/*     */ 
/*     */       
/* 250 */       il.append(new INVOKESTATIC(cpg
/* 251 */             .addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "startXslElement", "(Ljava/lang/String;Ljava/lang/String;Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;Lcom/sun/org/apache/xalan/internal/xsltc/DOM;I)Ljava/lang/String;")));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     translateContents(classGen, methodGen);
/*     */     
/* 262 */     if (!this._ignore) {
/* 263 */       il.append(methodGen.endElement());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateContents(ClassGenerator classGen, MethodGenerator methodGen) {
/* 273 */     int n = elementCount();
/* 274 */     for (int i = 0; i < n; i++) {
/* 275 */       SyntaxTreeNode item = getContents().get(i);
/* 276 */       if (!this._ignore || !(item instanceof XslAttribute))
/* 277 */         item.translate(classGen, methodGen); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/XslElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */