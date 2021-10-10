/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*     */ import com.sun.org.apache.bcel.internal.generic.ASTORE;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.GETFIELD;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import com.sun.org.apache.xml.internal.serializer.ElemDesc;
/*     */ import com.sun.org.apache.xml.internal.utils.XML11Char;
/*     */ import java.util.List;
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
/*     */ final class XslAttribute
/*     */   extends Instruction
/*     */ {
/*     */   private String _prefix;
/*     */   private AttributeValue _name;
/*  57 */   private AttributeValueTemplate _namespace = null;
/*     */   
/*     */   private boolean _ignore = false;
/*     */   
/*     */   private boolean _isLiteral = false;
/*     */ 
/*     */   
/*     */   public AttributeValue getName() {
/*  65 */     return this._name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void display(int indent) {
/*  72 */     indent(indent);
/*  73 */     Util.println("Attribute " + this._name);
/*  74 */     displayContents(indent + 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  81 */     boolean generated = false;
/*  82 */     SymbolTable stable = parser.getSymbolTable();
/*     */     
/*  84 */     String name = getAttribute("name");
/*  85 */     String namespace = getAttribute("namespace");
/*  86 */     QName qname = parser.getQName(name, false);
/*  87 */     String prefix = qname.getPrefix();
/*     */     
/*  89 */     if ((prefix != null && prefix.equals("xmlns")) || name.equals("xmlns")) {
/*  90 */       reportError(this, parser, "ILLEGAL_ATTR_NAME_ERR", name);
/*     */       
/*     */       return;
/*     */     } 
/*  94 */     this._isLiteral = Util.isLiteral(name);
/*  95 */     if (this._isLiteral && 
/*  96 */       !XML11Char.isXML11ValidQName(name)) {
/*  97 */       reportError(this, parser, "ILLEGAL_ATTR_NAME_ERR", name);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 103 */     SyntaxTreeNode parent = getParent();
/* 104 */     List<SyntaxTreeNode> siblings = parent.getContents();
/* 105 */     for (int i = 0; i < parent.elementCount(); i++) {
/* 106 */       SyntaxTreeNode item = siblings.get(i);
/* 107 */       if (item == this) {
/*     */         break;
/*     */       }
/* 110 */       if (!(item instanceof XslAttribute) && 
/* 111 */         !(item instanceof UseAttributeSets) && 
/* 112 */         !(item instanceof LiteralAttribute) && 
/* 113 */         !(item instanceof Text))
/*     */       {
/*     */ 
/*     */         
/* 117 */         if (!(item instanceof If) && 
/* 118 */           !(item instanceof Choose) && 
/* 119 */           !(item instanceof CopyOf) && 
/* 120 */           !(item instanceof VariableBase))
/*     */         {
/*     */           
/* 123 */           reportWarning(this, parser, "STRAY_ATTRIBUTE_ERR", name);
/*     */         }
/*     */       }
/*     */     } 
/* 127 */     if (namespace != null && namespace != "") {
/* 128 */       this._prefix = lookupPrefix(namespace);
/* 129 */       this._namespace = new AttributeValueTemplate(namespace, parser, this);
/*     */     
/*     */     }
/* 132 */     else if (prefix != null && prefix != "") {
/* 133 */       this._prefix = prefix;
/* 134 */       namespace = lookupNamespace(prefix);
/* 135 */       if (namespace != null) {
/* 136 */         this._namespace = new AttributeValueTemplate(namespace, parser, this);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 141 */     if (this._namespace != null) {
/*     */       
/* 143 */       if (this._prefix == null || this._prefix == "") {
/* 144 */         if (prefix != null) {
/* 145 */           this._prefix = prefix;
/*     */         } else {
/*     */           
/* 148 */           this._prefix = stable.generateNamespacePrefix();
/* 149 */           generated = true;
/*     */         }
/*     */       
/* 152 */       } else if (prefix != null && !prefix.equals(this._prefix)) {
/* 153 */         this._prefix = prefix;
/*     */       } 
/*     */       
/* 156 */       name = this._prefix + ":" + qname.getLocalPart();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 163 */       if (parent instanceof LiteralElement && !generated) {
/* 164 */         ((LiteralElement)parent).registerNamespace(this._prefix, namespace, stable, false);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 170 */     if (parent instanceof LiteralElement) {
/* 171 */       ((LiteralElement)parent).addAttribute(this);
/*     */     }
/*     */     
/* 174 */     this._name = AttributeValue.create(this, name, parser);
/* 175 */     parseChildren(parser);
/*     */   }
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 179 */     if (!this._ignore) {
/* 180 */       this._name.typeCheck(stable);
/* 181 */       if (this._namespace != null) {
/* 182 */         this._namespace.typeCheck(stable);
/*     */       }
/* 184 */       typeCheckContents(stable);
/*     */     } 
/* 186 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 193 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 194 */     InstructionList il = methodGen.getInstructionList();
/*     */     
/* 196 */     if (this._ignore)
/* 197 */       return;  this._ignore = true;
/*     */ 
/*     */     
/* 200 */     if (this._namespace != null) {
/*     */       
/* 202 */       il.append(methodGen.loadHandler());
/* 203 */       il.append(new PUSH(cpg, this._prefix));
/* 204 */       this._namespace.translate(classGen, methodGen);
/* 205 */       il.append(methodGen.namespace());
/*     */     } 
/*     */     
/* 208 */     if (!this._isLiteral) {
/*     */ 
/*     */       
/* 211 */       LocalVariableGen nameValue = methodGen.addLocalVariable2("nameValue", 
/* 212 */           Util.getJCRefType("Ljava/lang/String;"), null);
/*     */ 
/*     */ 
/*     */       
/* 216 */       this._name.translate(classGen, methodGen);
/* 217 */       nameValue.setStart(il.append(new ASTORE(nameValue.getIndex())));
/* 218 */       il.append(new ALOAD(nameValue.getIndex()));
/*     */ 
/*     */       
/* 221 */       int check = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "checkAttribQName", "(Ljava/lang/String;)V");
/*     */ 
/*     */ 
/*     */       
/* 225 */       il.append(new INVOKESTATIC(check));
/*     */ 
/*     */       
/* 228 */       il.append(methodGen.loadHandler());
/* 229 */       il.append(DUP);
/*     */ 
/*     */       
/* 232 */       nameValue.setEnd(il.append(new ALOAD(nameValue.getIndex())));
/*     */     } else {
/*     */       
/* 235 */       il.append(methodGen.loadHandler());
/* 236 */       il.append(DUP);
/*     */ 
/*     */       
/* 239 */       this._name.translate(classGen, methodGen);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 244 */     if (elementCount() == 1 && elementAt(0) instanceof Text) {
/* 245 */       il.append(new PUSH(cpg, ((Text)elementAt(0)).getText()));
/*     */     } else {
/*     */       
/* 248 */       il.append(classGen.loadTranslet());
/* 249 */       il.append(new GETFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "stringValueHandler", "Lcom/sun/org/apache/xalan/internal/xsltc/runtime/StringValueHandler;")));
/*     */ 
/*     */       
/* 252 */       il.append(DUP);
/* 253 */       il.append(methodGen.storeHandler());
/*     */       
/* 255 */       translateContents(classGen, methodGen);
/*     */       
/* 257 */       il.append(new INVOKEVIRTUAL(cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.StringValueHandler", "getValue", "()Ljava/lang/String;")));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 262 */     SyntaxTreeNode parent = getParent();
/* 263 */     if (parent instanceof LiteralElement && ((LiteralElement)parent)
/* 264 */       .allAttributesUnique()) {
/* 265 */       int flags = 0;
/* 266 */       ElemDesc elemDesc = ((LiteralElement)parent).getElemDesc();
/*     */ 
/*     */       
/* 269 */       if (elemDesc != null && this._name instanceof SimpleAttributeValue) {
/* 270 */         String attrName = ((SimpleAttributeValue)this._name).toString();
/* 271 */         if (elemDesc.isAttrFlagSet(attrName, 4)) {
/* 272 */           flags |= 0x2;
/*     */         }
/* 274 */         else if (elemDesc.isAttrFlagSet(attrName, 2)) {
/* 275 */           flags |= 0x4;
/*     */         } 
/*     */       } 
/* 278 */       il.append(new PUSH(cpg, flags));
/* 279 */       il.append(methodGen.uniqueAttribute());
/*     */     }
/*     */     else {
/*     */       
/* 283 */       il.append(methodGen.attribute());
/*     */     } 
/*     */ 
/*     */     
/* 287 */     il.append(methodGen.storeHandler());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/XslAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */