/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import java.util.List;
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
/*     */ final class UnsupportedElement
/*     */   extends SyntaxTreeNode
/*     */ {
/*  44 */   private Vector _fallbacks = null;
/*  45 */   private ErrorMsg _message = null;
/*     */ 
/*     */   
/*     */   private boolean _isExtension = false;
/*     */ 
/*     */   
/*     */   public UnsupportedElement(String uri, String prefix, String local, boolean isExtension) {
/*  52 */     super(uri, prefix, local);
/*  53 */     this._isExtension = isExtension;
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
/*     */   public void setErrorMessage(ErrorMsg message) {
/*  66 */     this._message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void display(int indent) {
/*  73 */     indent(indent);
/*  74 */     Util.println("Unsupported element = " + this._qname.getNamespace() + ":" + this._qname
/*  75 */         .getLocalPart());
/*  76 */     displayContents(indent + 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processFallbacks(Parser parser) {
/*  85 */     List<SyntaxTreeNode> children = getContents();
/*  86 */     if (children != null) {
/*  87 */       int count = children.size();
/*  88 */       for (int i = 0; i < count; i++) {
/*  89 */         SyntaxTreeNode child = children.get(i);
/*  90 */         if (child instanceof Fallback) {
/*  91 */           Fallback fallback = (Fallback)child;
/*  92 */           fallback.activate();
/*  93 */           fallback.parseContents(parser);
/*  94 */           if (this._fallbacks == null) {
/*  95 */             this._fallbacks = new Vector();
/*     */           }
/*  97 */           this._fallbacks.addElement(child);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/* 107 */     processFallbacks(parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 114 */     if (this._fallbacks != null) {
/* 115 */       int count = this._fallbacks.size();
/* 116 */       for (int i = 0; i < count; i++) {
/* 117 */         Fallback fallback = this._fallbacks.elementAt(i);
/* 118 */         fallback.typeCheck(stable);
/*     */       } 
/*     */     } 
/* 121 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 128 */     if (this._fallbacks != null) {
/* 129 */       int count = this._fallbacks.size();
/* 130 */       for (int i = 0; i < count; i++) {
/* 131 */         Fallback fallback = this._fallbacks.elementAt(i);
/* 132 */         fallback.translate(classGen, methodGen);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 141 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 142 */       InstructionList il = methodGen.getInstructionList();
/*     */       
/* 144 */       int unsupportedElem = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "unsupported_ElementF", "(Ljava/lang/String;Z)V");
/*     */       
/* 146 */       il.append(new PUSH(cpg, getQName().toString()));
/* 147 */       il.append(new PUSH(cpg, this._isExtension));
/* 148 */       il.append(new INVOKESTATIC(unsupportedElem));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/UnsupportedElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */