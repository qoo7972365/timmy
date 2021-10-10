/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.IF_ICMPNE;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.SIPUSH;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MultiHashtable;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CastExpr
/*     */   extends Expression
/*     */ {
/*     */   private final Expression _left;
/*  54 */   private static final MultiHashtable<Type, Type> InternalTypeMap = new MultiHashtable<>();
/*     */ 
/*     */   
/*     */   static {
/*  58 */     InternalTypeMap.put(Type.Boolean, Type.Boolean);
/*  59 */     InternalTypeMap.put(Type.Boolean, Type.Real);
/*  60 */     InternalTypeMap.put(Type.Boolean, Type.String);
/*  61 */     InternalTypeMap.put(Type.Boolean, Type.Reference);
/*  62 */     InternalTypeMap.put(Type.Boolean, Type.Object);
/*     */     
/*  64 */     InternalTypeMap.put(Type.Real, Type.Real);
/*  65 */     InternalTypeMap.put(Type.Real, Type.Int);
/*  66 */     InternalTypeMap.put(Type.Real, Type.Boolean);
/*  67 */     InternalTypeMap.put(Type.Real, Type.String);
/*  68 */     InternalTypeMap.put(Type.Real, Type.Reference);
/*  69 */     InternalTypeMap.put(Type.Real, Type.Object);
/*     */     
/*  71 */     InternalTypeMap.put(Type.Int, Type.Int);
/*  72 */     InternalTypeMap.put(Type.Int, Type.Real);
/*  73 */     InternalTypeMap.put(Type.Int, Type.Boolean);
/*  74 */     InternalTypeMap.put(Type.Int, Type.String);
/*  75 */     InternalTypeMap.put(Type.Int, Type.Reference);
/*  76 */     InternalTypeMap.put(Type.Int, Type.Object);
/*     */     
/*  78 */     InternalTypeMap.put(Type.String, Type.String);
/*  79 */     InternalTypeMap.put(Type.String, Type.Boolean);
/*  80 */     InternalTypeMap.put(Type.String, Type.Real);
/*  81 */     InternalTypeMap.put(Type.String, Type.Reference);
/*  82 */     InternalTypeMap.put(Type.String, Type.Object);
/*     */     
/*  84 */     InternalTypeMap.put(Type.NodeSet, Type.NodeSet);
/*  85 */     InternalTypeMap.put(Type.NodeSet, Type.Boolean);
/*  86 */     InternalTypeMap.put(Type.NodeSet, Type.Real);
/*  87 */     InternalTypeMap.put(Type.NodeSet, Type.String);
/*  88 */     InternalTypeMap.put(Type.NodeSet, Type.Node);
/*  89 */     InternalTypeMap.put(Type.NodeSet, Type.Reference);
/*  90 */     InternalTypeMap.put(Type.NodeSet, Type.Object);
/*     */     
/*  92 */     InternalTypeMap.put(Type.Node, Type.Node);
/*  93 */     InternalTypeMap.put(Type.Node, Type.Boolean);
/*  94 */     InternalTypeMap.put(Type.Node, Type.Real);
/*  95 */     InternalTypeMap.put(Type.Node, Type.String);
/*  96 */     InternalTypeMap.put(Type.Node, Type.NodeSet);
/*  97 */     InternalTypeMap.put(Type.Node, Type.Reference);
/*  98 */     InternalTypeMap.put(Type.Node, Type.Object);
/*     */     
/* 100 */     InternalTypeMap.put(Type.ResultTree, Type.ResultTree);
/* 101 */     InternalTypeMap.put(Type.ResultTree, Type.Boolean);
/* 102 */     InternalTypeMap.put(Type.ResultTree, Type.Real);
/* 103 */     InternalTypeMap.put(Type.ResultTree, Type.String);
/* 104 */     InternalTypeMap.put(Type.ResultTree, Type.NodeSet);
/* 105 */     InternalTypeMap.put(Type.ResultTree, Type.Reference);
/* 106 */     InternalTypeMap.put(Type.ResultTree, Type.Object);
/*     */     
/* 108 */     InternalTypeMap.put(Type.Reference, Type.Reference);
/* 109 */     InternalTypeMap.put(Type.Reference, Type.Boolean);
/* 110 */     InternalTypeMap.put(Type.Reference, Type.Int);
/* 111 */     InternalTypeMap.put(Type.Reference, Type.Real);
/* 112 */     InternalTypeMap.put(Type.Reference, Type.String);
/* 113 */     InternalTypeMap.put(Type.Reference, Type.Node);
/* 114 */     InternalTypeMap.put(Type.Reference, Type.NodeSet);
/* 115 */     InternalTypeMap.put(Type.Reference, Type.ResultTree);
/* 116 */     InternalTypeMap.put(Type.Reference, Type.Object);
/*     */     
/* 118 */     InternalTypeMap.put(Type.Object, Type.String);
/*     */     
/* 120 */     InternalTypeMap.put(Type.Void, Type.String);
/*     */     
/* 122 */     InternalTypeMap.makeUnmodifiable();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _typeTest = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public CastExpr(Expression left, Type type) throws TypeCheckError {
/* 132 */     this._left = left;
/* 133 */     this._type = type;
/*     */     
/* 135 */     if (this._left instanceof Step && this._type == Type.Boolean) {
/* 136 */       Step step = (Step)this._left;
/* 137 */       if (step.getAxis() == 13 && step.getNodeType() != -1) {
/* 138 */         this._typeTest = true;
/*     */       }
/*     */     } 
/*     */     
/* 142 */     setParser(left.getParser());
/* 143 */     setParent(left.getParent());
/* 144 */     left.setParent(this);
/* 145 */     typeCheck(left.getParser().getSymbolTable());
/*     */   }
/*     */   
/*     */   public Expression getExpr() {
/* 149 */     return this._left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPositionCall() {
/* 157 */     return this._left.hasPositionCall();
/*     */   }
/*     */   
/*     */   public boolean hasLastCall() {
/* 161 */     return this._left.hasLastCall();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 165 */     return "cast(" + this._left + ", " + this._type + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 175 */     Type tleft = this._left.getType();
/* 176 */     if (tleft == null) {
/* 177 */       tleft = this._left.typeCheck(stable);
/*     */     }
/* 179 */     if (tleft instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType) {
/* 180 */       tleft = Type.Node;
/*     */     }
/* 182 */     else if (tleft instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType) {
/* 183 */       tleft = Type.ResultTree;
/*     */     } 
/* 185 */     if (InternalTypeMap.maps(tleft, this._type) != null) {
/* 186 */       return this._type;
/*     */     }
/*     */     
/* 189 */     throw new TypeCheckError(new ErrorMsg("DATA_CONVERSION_ERR", tleft
/* 190 */           .toString(), this._type.toString()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen) {
/* 196 */     Type ltype = this._left.getType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (this._typeTest) {
/* 202 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 203 */       InstructionList il = methodGen.getInstructionList();
/*     */       
/* 205 */       int idx = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getExpandedTypeID", "(I)I");
/*     */ 
/*     */       
/* 208 */       il.append(new SIPUSH((short)((Step)this._left).getNodeType()));
/* 209 */       il.append(methodGen.loadDOM());
/* 210 */       il.append(methodGen.loadContextNode());
/* 211 */       il.append(new INVOKEINTERFACE(idx, 2));
/* 212 */       this._falseList.add(il.append(new IF_ICMPNE(null)));
/*     */     }
/*     */     else {
/*     */       
/* 216 */       this._left.translate(classGen, methodGen);
/* 217 */       if (this._type != ltype) {
/* 218 */         this._left.startIterator(classGen, methodGen);
/* 219 */         if (this._type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.BooleanType) {
/* 220 */           FlowList fl = ltype.translateToDesynthesized(classGen, methodGen, this._type);
/*     */           
/* 222 */           if (fl != null) {
/* 223 */             this._falseList.append(fl);
/*     */           }
/*     */         } else {
/*     */           
/* 227 */           ltype.translateTo(classGen, methodGen, this._type);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 234 */     Type ltype = this._left.getType();
/* 235 */     this._left.translate(classGen, methodGen);
/* 236 */     if (!this._type.identicalTo(ltype)) {
/* 237 */       this._left.startIterator(classGen, methodGen);
/* 238 */       ltype.translateTo(classGen, methodGen, this._type);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/CastExpr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */