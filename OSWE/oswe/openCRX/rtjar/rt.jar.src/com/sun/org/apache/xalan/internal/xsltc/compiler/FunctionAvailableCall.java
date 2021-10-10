/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ final class FunctionAvailableCall
/*     */   extends FunctionCall
/*     */ {
/*     */   private Expression _arg;
/*  47 */   private String _nameOfFunct = null;
/*  48 */   private String _namespaceOfFunct = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _isFunctionAvailable = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FunctionAvailableCall(QName fname, Vector<Expression> arguments) {
/*  58 */     super(fname, arguments);
/*  59 */     this._arg = arguments.elementAt(0);
/*  60 */     this._type = null;
/*     */     
/*  62 */     if (this._arg instanceof LiteralExpr) {
/*  63 */       LiteralExpr arg = (LiteralExpr)this._arg;
/*  64 */       this._namespaceOfFunct = arg.getNamespace();
/*  65 */       this._nameOfFunct = arg.getValue();
/*     */       
/*  67 */       if (!isInternalNamespace()) {
/*  68 */         this._isFunctionAvailable = hasMethods();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/*  78 */     if (this._type != null) {
/*  79 */       return this._type;
/*     */     }
/*  81 */     if (this._arg instanceof LiteralExpr) {
/*  82 */       return this._type = Type.Boolean;
/*     */     }
/*  84 */     ErrorMsg err = new ErrorMsg("NEED_LITERAL_ERR", "function-available", this);
/*     */     
/*  86 */     throw new TypeCheckError(err);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evaluateAtCompileTime() {
/*  95 */     return getResult() ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasMethods() {
/* 105 */     String className = getClassNameFromUri(this._namespaceOfFunct);
/*     */ 
/*     */     
/* 108 */     String methodName = null;
/* 109 */     int colonIndex = this._nameOfFunct.indexOf(':');
/* 110 */     if (colonIndex > 0) {
/* 111 */       String functionName = this._nameOfFunct.substring(colonIndex + 1);
/* 112 */       int lastDotIndex = functionName.lastIndexOf('.');
/* 113 */       if (lastDotIndex > 0) {
/* 114 */         methodName = functionName.substring(lastDotIndex + 1);
/* 115 */         if (className != null && className.length() != 0) {
/* 116 */           className = className + "." + functionName.substring(0, lastDotIndex);
/*     */         } else {
/* 118 */           className = functionName.substring(0, lastDotIndex);
/*     */         } 
/*     */       } else {
/* 121 */         methodName = functionName;
/*     */       } 
/*     */     } else {
/* 124 */       methodName = this._nameOfFunct;
/*     */     } 
/* 126 */     if (className == null || methodName == null) {
/* 127 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 131 */     if (methodName.indexOf('-') > 0) {
/* 132 */       methodName = replaceDash(methodName);
/*     */     }
/*     */     try {
/* 135 */       Class<?> clazz = ObjectFactory.findProviderClass(className, true);
/*     */       
/* 137 */       if (clazz == null) {
/* 138 */         return false;
/*     */       }
/*     */       
/* 141 */       Method[] methods = clazz.getMethods();
/*     */       
/* 143 */       for (int i = 0; i < methods.length; i++) {
/* 144 */         int mods = methods[i].getModifiers();
/*     */         
/* 146 */         if (Modifier.isPublic(mods) && Modifier.isStatic(mods) && methods[i]
/* 147 */           .getName().equals(methodName))
/*     */         {
/* 149 */           return true;
/*     */         }
/*     */       }
/*     */     
/* 153 */     } catch (ClassNotFoundException e) {
/* 154 */       return false;
/*     */     } 
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getResult() {
/* 164 */     if (this._nameOfFunct == null) {
/* 165 */       return false;
/*     */     }
/*     */     
/* 168 */     if (isInternalNamespace()) {
/* 169 */       Parser parser = getParser();
/* 170 */       this
/* 171 */         ._isFunctionAvailable = parser.functionSupported(Util.getLocalName(this._nameOfFunct));
/*     */     } 
/* 173 */     return this._isFunctionAvailable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInternalNamespace() {
/* 180 */     return (this._namespaceOfFunct == null || this._namespaceOfFunct
/* 181 */       .equals("") || this._namespaceOfFunct
/* 182 */       .equals("http://xml.apache.org/xalan/xsltc"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 191 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 192 */     methodGen.getInstructionList().append(new PUSH(cpg, getResult()));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/FunctionAvailableCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */