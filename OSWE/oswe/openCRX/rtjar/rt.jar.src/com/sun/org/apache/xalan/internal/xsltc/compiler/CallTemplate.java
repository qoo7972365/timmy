/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CallTemplate
/*     */   extends Instruction
/*     */ {
/*     */   private QName _name;
/*  52 */   private SyntaxTreeNode[] _parameters = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private Template _calleeTemplate = null;
/*     */   
/*     */   public void display(int indent) {
/*  60 */     indent(indent);
/*  61 */     System.out.print("CallTemplate");
/*  62 */     Util.println(" name " + this._name);
/*  63 */     displayContents(indent + 4);
/*     */   }
/*     */   
/*     */   public boolean hasWithParams() {
/*  67 */     return (elementCount() > 0);
/*     */   }
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  71 */     String name = getAttribute("name");
/*  72 */     if (name.length() > 0) {
/*  73 */       if (!XML11Char.isXML11ValidQName(name)) {
/*  74 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/*  75 */         parser.reportError(3, err);
/*     */       } 
/*  77 */       this._name = parser.getQNameIgnoreDefaultNs(name);
/*     */     } else {
/*     */       
/*  80 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
/*     */     } 
/*  82 */     parseChildren(parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/*  89 */     Template template = stable.lookupTemplate(this._name);
/*  90 */     if (template != null) {
/*  91 */       typeCheckContents(stable);
/*     */     } else {
/*     */       
/*  94 */       ErrorMsg err = new ErrorMsg("TEMPLATE_UNDEF_ERR", this._name, this);
/*  95 */       throw new TypeCheckError(err);
/*     */     } 
/*  97 */     return Type.Void;
/*     */   }
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 101 */     Stylesheet stylesheet = classGen.getStylesheet();
/* 102 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 103 */     InstructionList il = methodGen.getInstructionList();
/*     */ 
/*     */     
/* 106 */     if (stylesheet.hasLocalParams() || hasContents()) {
/* 107 */       this._calleeTemplate = getCalleeTemplate();
/*     */ 
/*     */       
/* 110 */       if (this._calleeTemplate != null) {
/* 111 */         buildParameterList();
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 117 */         int push = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "pushParamFrame", "()V");
/*     */ 
/*     */         
/* 120 */         il.append(classGen.loadTranslet());
/* 121 */         il.append(new INVOKEVIRTUAL(push));
/* 122 */         translateContents(classGen, methodGen);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 127 */     String className = stylesheet.getClassName();
/* 128 */     String methodName = Util.escape(this._name.toString());
/*     */ 
/*     */     
/* 131 */     il.append(classGen.loadTranslet());
/* 132 */     il.append(methodGen.loadDOM());
/* 133 */     il.append(methodGen.loadIterator());
/* 134 */     il.append(methodGen.loadHandler());
/* 135 */     il.append(methodGen.loadCurrentNode());
/*     */ 
/*     */     
/* 138 */     StringBuffer methodSig = new StringBuffer("(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;I");
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (this._calleeTemplate != null) {
/* 143 */       int numParams = this._parameters.length;
/*     */       
/* 145 */       for (int i = 0; i < numParams; i++) {
/* 146 */         SyntaxTreeNode node = this._parameters[i];
/* 147 */         methodSig.append("Ljava/lang/Object;");
/*     */ 
/*     */         
/* 150 */         if (node instanceof Param) {
/* 151 */           il.append(ACONST_NULL);
/*     */         } else {
/*     */           
/* 154 */           node.translate(classGen, methodGen);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 160 */     methodSig.append(")V");
/* 161 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref(className, methodName, methodSig
/*     */             
/* 163 */             .toString())));
/*     */ 
/*     */     
/* 166 */     if (this._parameters != null) {
/* 167 */       for (int i = 0; i < this._parameters.length; i++) {
/* 168 */         if (this._parameters[i] instanceof WithParam) {
/* 169 */           ((WithParam)this._parameters[i]).releaseResultTree(classGen, methodGen);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 176 */     if (this._calleeTemplate == null && (stylesheet.hasLocalParams() || hasContents())) {
/*     */       
/* 178 */       int pop = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "popParamFrame", "()V");
/*     */ 
/*     */       
/* 181 */       il.append(classGen.loadTranslet());
/* 182 */       il.append(new INVOKEVIRTUAL(pop));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Template getCalleeTemplate() {
/* 193 */     Template foundTemplate = getXSLTC().getParser().getSymbolTable().lookupTemplate(this._name);
/*     */     
/* 195 */     return foundTemplate.isSimpleNamedTemplate() ? foundTemplate : null;
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
/*     */   private void buildParameterList() {
/* 207 */     Vector<Param> defaultParams = this._calleeTemplate.getParameters();
/* 208 */     int numParams = defaultParams.size();
/* 209 */     this._parameters = new SyntaxTreeNode[numParams];
/* 210 */     for (int i = 0; i < numParams; i++) {
/* 211 */       this._parameters[i] = defaultParams.elementAt(i);
/*     */     }
/*     */ 
/*     */     
/* 215 */     int count = elementCount();
/* 216 */     for (int j = 0; j < count; j++) {
/* 217 */       Object node = elementAt(j);
/*     */ 
/*     */       
/* 220 */       if (node instanceof WithParam) {
/* 221 */         WithParam withParam = (WithParam)node;
/* 222 */         QName name = withParam.getName();
/*     */ 
/*     */         
/* 225 */         for (int k = 0; k < numParams; k++) {
/* 226 */           SyntaxTreeNode parm = this._parameters[k];
/* 227 */           if (parm instanceof Param && ((Param)parm)
/* 228 */             .getName().equals(name)) {
/* 229 */             withParam.setDoParameterOptimization(true);
/* 230 */             this._parameters[k] = withParam;
/*     */             break;
/*     */           } 
/* 233 */           if (parm instanceof WithParam && ((WithParam)parm)
/* 234 */             .getName().equals(name)) {
/* 235 */             withParam.setDoParameterOptimization(true);
/* 236 */             this._parameters[k] = withParam;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/CallTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */